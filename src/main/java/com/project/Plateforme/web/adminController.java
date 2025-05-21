package com.project.Plateforme.web;

import com.project.Plateforme.core.bo.*;
import com.project.Plateforme.core.repository.*;
import com.project.Plateforme.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.project.Plateforme.core.bo.annotateur;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminService adminService;
    @Autowired
    private tacheRepository TacheRepository;
    @Autowired
    private datasetService datasetService;
    @Autowired
    private TextPairService textPairService;
    @Autowired
    private textPairRepository textPairRepository;
    @Autowired
    private datasetRepository datasetRepository;
    @Autowired
    private com.project.Plateforme.service.annotateurService annotateurService;
    @Autowired
    private com.project.Plateforme.core.repository.annotateurRepository annotateurRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private roleRepository Rolerepository;
    @Autowired
    private classePossiblesRepository classePossiblesRepository;
    @Autowired
    private classePossiblesService classePossiblesService;
    @Autowired
    private annotationService annotationService;
    @Autowired
    private annotationRepository annotationRepository;
    @GetMapping
    public String ShowAdminPage() {
        return "admin";
    }

    @GetMapping("/creer-dataset")
    public String showCreateDatasetPage() {
        return "creer-dataset";
    }


    @PostMapping("/creer-dataset")
    public String createDataset(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("classList") String classList,
                                Model model) {

        dataset newDataset = new dataset(name, description, classList);

        if (!file.isEmpty()) {
            try {
                // ➔ Utiliser chemin absolu basé sur ton projet
                String baseDir = System.getProperty("user.dir"); // C:\Users\hp\Desktop\Plateforme
                String uploadDir = baseDir + "/uploads/";
                Path uploadPath = Path.of(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                newDataset.setFilePath(filePath.toString()); // Chemin absolu pour éviter les erreurs

            } catch (IOException e) {
                model.addAttribute("error", "Échec du téléchargement du fichier.");
                return "creer-dataset";
            }
        }

        // 1. Sauvegarder d'abord le dataset pour obtenir son ID
        datasetService.saveDataset(newDataset);

        // 2. Ensuite, parser le fichier CSV pour créer les TextPairs
        try {
            Path csvPath = Paths.get(newDataset.getFilePath());
            List<String> lines = Files.readAllLines(csvPath);

            for (int i = 1; i < lines.size(); i++) { // Commencer à 1 pour ignorer l'entête
                String line = lines.get(i);
                String[] parts = line.split(",");

                if (parts.length >= 2) {
                    String text1 = parts[0].trim();
                    String text2 = parts[1].trim();

                    TextPair textPair = new TextPair();
                    textPair.setText1(text1);
                    textPair.setText2(text2);
                    textPair.setDataset(newDataset); // Associer au dataset
                    textPairService.saveTextPair(textPair);
                }
            }
            // 2. Ajouter les classes possibles
            String[] classesArray = classList.split(";");
            List<classePossibles> classePossiblesList = new ArrayList<>();

            for (String nomClasse : classesArray) {
                classePossibles c = new classePossibles(nomClasse.trim());
                c.setDataset(newDataset); // Associer au dataset
                classePossiblesList.add(c);
            }



// Sinon, tu dois enregistrer manuellement :
            classePossiblesList.forEach(classePossiblesRepository::save);
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors de la lecture du fichier CSV.");
            return "creer-dataset";
        }

        model.addAttribute("message", "Dataset créé avec succès!");
        return "redirect:/admin";
    }


    @GetMapping("/lister-dataset")
    public String showListerDatasetPage(Model model) {
        List<dataset> datasetList = datasetService.getAllDatasets();
        model.addAttribute("datasetList", datasetList);
        Map<Long, Float> avancements = new HashMap<>();
        for (dataset d : datasetList) {
            float avancement = annotationService.Getavancement(d.getId());
            avancements.put(d.getId(), avancement);
        }
        model.addAttribute("avancements", avancements);
        return "lister-dataset";
    }

    @GetMapping("/lister-dataset/details/{id}")
    public String afficherDetailsDataset(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size, Model model) {
        dataset dataset = datasetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dataset non trouvé"));

        model.addAttribute("dataset", dataset);
        model.addAttribute("avancement", annotationService.Getavancement(dataset.getId()));
        // Récupérer les textPairs si tu les gardes dans dataset (facultatif si tu relies TextPair à Tache)
        Pageable pageable = PageRequest.of(page, size);
        Page<TextPair> textPairPage = textPairRepository.findByDataset(dataset, pageable);
        model.addAttribute("textPairPage", textPairPage);
        // Récupérer les annotateurs à partir des tâches liées à ce dataset
        List<annotateur> annotateurs = TacheRepository.findAnnotateursByDatasetId(id);
        model.addAttribute("annotateurs", annotateurs);

        return "details"; // nom de la vue HTML
    }

    @GetMapping("/lister-dataset/assigner/{id}")
    public String afficherAnnotateurAssignerDataset(@PathVariable Long id, Model model) {
        model.addAttribute("datasetId", id);

        List<annotateur> tousLesAnnotateursActifs = annotateurService.getAllAnnotateur()
                .stream()
                .filter(annotateur::getActif)
                .collect(Collectors.toList());

        // Récupérer les annotateurs déjà assignés au dataset
        List<tache> tachesExistantes = TacheRepository.findByDatasetId(id);
        Set<Long> idsAnnotateursAssignés = tachesExistantes.stream()
                .map(t -> t.getAnnotateur().getId())
                .collect(Collectors.toSet());

        // Filtrer pour ne garder que les non encore assignés
        List<annotateur> annotateursDisponibles = tousLesAnnotateursActifs.stream()
                .filter(a -> !idsAnnotateursAssignés.contains(a.getId()))
                .collect(Collectors.toList());

        model.addAttribute("annotateurList", annotateursDisponibles);
        return "assigner";
    }


    @PostMapping("/assigner-dataset")
    public String assignerDataset(@RequestParam List<Long> annotateurIds, @RequestParam Long datasetId, Model model) {
        dataset dataset = datasetRepository.findById(datasetId).orElse(null);
        if (dataset == null) {
            throw new RuntimeException("dataset introuvable");
        }

        List<TextPair> textPairs = dataset.getTextPairs();
        List<annotateur> annotateurList = annotateurRepository.findAllById(annotateurIds);

        if (textPairs == null || annotateurList == null || annotateurList.isEmpty()) {
            model.addAttribute("error", "Aucun TextPair ou annotateur disponible!");
            return "error"; // ou rediriger ailleurs
        }

        int size = annotateurList.size();
        int conteur = 0;

        // Créer les tâches une fois par annotateur
        Map<annotateur, tache> tacheMap = new HashMap<>();

        for (annotateur annot : annotateurList) {
            tache t = new tache();
            t.setAnnotateur(annot);
            t.setDataset(dataset);
            LocalDate dans3Jours = LocalDate.now().plusDays(3);
            Date dateLimite = Date.from(dans3Jours.atStartOfDay(ZoneId.systemDefault()).toInstant());
            t.setDateLimite(dateLimite);
            TacheRepository.save(t);
            tacheMap.put(annot, t); // stocker dans une map pour y accéder plus tard
        }

        // Répartir les TextPair dans les tâches existantes (round-robin)
        for (TextPair textPair : textPairs) {
            annotateur a = annotateurList.get(conteur % size);
            tache tache = tacheMap.get(a);
            textPair.setTache(tache);
            textPairRepository.save(textPair);
            conteur++;
        }

        return "redirect:/admin/lister-dataset";
    }

    @GetMapping("/gerer_annotateur")
    public String gererAnnotateur(Model model) {
        List<annotateur> annotateurList = annotateurService.getAllAnnotateur();
        model.addAttribute("annotateurList", annotateurList);
        return "gerer-annotateur";
    }
    @GetMapping("/gerer_annotateur/modifier/{id}")
    public String ModifierAnnotateur(@PathVariable Long id, Model model) {
        annotateur annotateur = annotateurRepository.findById(id).orElse(null);
        if (annotateur == null) {
            throw new RuntimeException("annotateur introuvable");
        }
        model.addAttribute("annotateur", annotateur);
        return "modifier";
    }
    @PostMapping("/gerer_annotateur/modifier")
    public String enregistrerLaModification(@ModelAttribute annotateur annotateur) {
        System.out.println(annotateur.getNom());
        System.out.println(annotateur.getPrenom());
        System.out.println(annotateur.getId());
        if (annotateur.getId() == null) {
            throw new RuntimeException("ID manquant dans le formulaire !");
        }

        annotateur existing = annotateurRepository.findById(annotateur.getId())
                .orElseThrow(() -> new IllegalArgumentException("Annotateur introuvable avec ID : " + annotateur.getId()));

        // Debug temporaire
        System.out.println("*****ID reçu : " + annotateur.getId());

        existing.setLogin(annotateur.getLogin());
        existing.setNom(annotateur.getNom());
        existing.setPrenom(annotateur.getPrenom());
        existing.setPassword(existing.getPassword());

        existing.setActif(true);

        annotateurRepository.save(existing); // Effectuer l'update

        return "redirect:/admin/gerer_annotateur";
    }

    @GetMapping("/gerer_annotateur/ajouter")
    public String ajouterAnnotateur(Model model) {
        model.addAttribute("annotateur", new annotateur());
        return "ajouter-annotateur";
    }
    @PostMapping("/gerer_annotateur/ajouter")
    public String enregistrerAnnotateur(@ModelAttribute annotateur annotateur) {
        String motDePasse = userService.genererMotDePasseAleatoire(5);
        Role roleAnnotateur = Rolerepository.findById(2);
        annotateur.setRole(roleAnnotateur);
        annotateur.setPassword(motDePasse);
        annotateur.setActif(true);
        annotateurRepository.save(annotateur);
        return "redirect:/admin/gerer_annotateur";
    }
    @PostMapping("/gerer_annotateur/supprimer/{id}")
    public String supprimerAnnotateur(@PathVariable Long id, Model model) {
        annotateur annotateur = annotateurRepository.findById(id).orElse(null);
        if (annotateur == null) {
            throw new RuntimeException("annotateur introuvable");
        }
        annotateur.setActif(false);
        annotateurRepository.save(annotateur);
        return "redirect:/admin/gerer_annotateur";
    }
    @GetMapping("/importer_dataset")
    public String importerDataset(Model model) {
        List<dataset> datasetList = datasetService.getAllDatasets();
        model.addAttribute("datasetList", datasetList);
        return "importer-dataset";
    }
    @GetMapping("/importer_dataset/csv_file/{id}")
    public void exporterDatasetCSV(@PathVariable Long id, HttpServletResponse response) {
        Optional<dataset> optionalDataset = datasetRepository.findById(id);
        if (optionalDataset.isEmpty()) {
            throw new RuntimeException("Dataset introuvable avec l'id: " + id);
        }

        dataset dataset = optionalDataset.get();
        List<TextPair> textPairs = textPairService.findByDataset(dataset);

        // Define the file name
        String fileName = "exported_dataset_" + id + ".csv";

        // Set the response content type and disposition
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,text1,text2,label,Annotateur"); // Write header

            for (TextPair tp : textPairs) {
                String label = annotationService.findLabelByTextPairById(tp.getId()).orElse("non-annoté");
                annotateur annotateur = tp.getTache().getAnnotateur();
                String annotateurName = annotateur != null ? annotateur.getNom()+" "+annotateur.getPrenom() : "";
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n", tp.getId(),tp.getText1(), tp.getText2(), label, annotateurName); // Write data
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'écriture du fichier CSV", e);
        }
    }





}

