package com.project.Plateforme.web;

import com.project.Plateforme.core.bo.*;
import com.project.Plateforme.core.repository.annotationRepository;
import com.project.Plateforme.core.repository.tacheRepository;
import com.project.Plateforme.service.TextPairService;
import com.project.Plateforme.service.UserService;
import com.project.Plateforme.service.annotateurService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.Plateforme.core.repository.textPairRepository;

import java.util.*;

@Controller
@RequestMapping("/annotateur")
public class annotateurController{
    @Autowired
    private annotateurService annotateurService;
    @Autowired
    private UserService userService;
    @Autowired
    private annotateurService AnnotateurService;
    @Autowired
    private tacheRepository TacheRepository;
    @Autowired
    private textPairRepository TextPairRepository;
    @Autowired
    private com.project.Plateforme.core.repository.annotateurRepository annotateurRepository;
    @Autowired
    private annotationRepository AnnotationRepository;
    @Autowired
    private com.project.Plateforme.service.annotationService annotationService;
    @Autowired
    private com.project.Plateforme.service.datasetService datasetService;

    @GetMapping
    public String showAnnotateurPage(HttpSession session, Model model) {
        User annotateur =(User) session.getAttribute("user");
        model.addAttribute("annotateur", annotateur);
        return "annotateur";
    }
    @GetMapping("/lister-taches")
    public String showTaches(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user instanceof annotateur) {
            annotateur annot = (annotateur) user;
            List<tache> taches = annot.getTache();  // Accéder à la liste des tâches
            model.addAttribute("taches", taches);
            Map<Long, Float> avancements = new HashMap<>();
            List<dataset> datasetList = datasetService.getAllDatasets();
            for (dataset d : datasetList) {
                float avancement = annotationService.Getavancement(d.getId());
                avancements.put(d.getId(), avancement);
            }
            model.addAttribute("avancements", avancements);
            return "taches";  // Afficher uniquement la liste des tâches
        }

        return "redirect:/annotateur";  // Rediriger si ce n'est pas un annotateur
    }
    @GetMapping("/annoter")
    public String showAnnoterPage(@RequestParam Integer tacheId,
                                  @RequestParam(defaultValue = "0") int index,
                                  Model model) {
        tache tache = TacheRepository.findById(tacheId).orElse(null);
        if (tache == null) {
            model.addAttribute("error", "Tâche introuvable");
            return "redirect:/lister-taches";
        }

        List<TextPair> textPairList = tache.getTextPairs();
        if (textPairList == null || textPairList.isEmpty()) {
            model.addAttribute("error", "Aucun TextPair pour cette tâche");
            return "redirect:/lister-taches";
        }

        if (index < 0 || index >= textPairList.size()) {
            model.addAttribute("error", "Index invalide");
            return "redirect:/lister-taches";
        }

        model.addAttribute("textPair", textPairList.get(index));
        model.addAttribute("index", index);
        model.addAttribute("total", textPairList.size());

        return "annotation";
    }
    @PostMapping("/terminer-annotation")
    public String terminerAnnotations(@RequestParam String classe,
                                      @RequestParam Long textPairId,
                                      @RequestParam Integer tacheId,
                                      @RequestParam Integer index,
                                      Model model) {
        TextPair textPair = TextPairRepository.getTextPairById(textPairId);
        annotateur annotateur = TacheRepository.getAnnotateurByTacheId(tacheId);

        // Chercher si une annotation existe déjà
        Optional<Annotation> existingAnnotation = AnnotationRepository.findByAnnotateurAndTextPair(annotateur, textPair);

        Annotation annotation;
        if (existingAnnotation.isPresent()) {
            // Modifier l'existante
            annotation = existingAnnotation.get();
            annotation.setClasseChoisie(classe);
        } else {
            // Créer une nouvelle
            annotation = new Annotation();
            annotation.setTextPair(textPair);
            annotation.setAnnotateur(annotateur);
            annotation.setClasseChoisie(classe);
        }

        AnnotationRepository.save(annotation);  // save() fait insert ou update automatiquement
        List<TextPair> textPairs = TextPairRepository.getTextPairByTacheId(tacheId);
        int nextIndex = index+1;

        // ↪️ Si on dépasse la fin, on retourne au début (index = 0)
        if (nextIndex >= textPairs.size()) {
            nextIndex = 0;
        }
        return "redirect:/annotateur/annoter?tacheId=" + tacheId + "&index=" + (nextIndex);    }




}
