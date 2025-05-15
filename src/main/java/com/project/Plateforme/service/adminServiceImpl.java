package com.project.Plateforme.service;

import com.project.Plateforme.core.bo.dataset;
import com.project.Plateforme.core.repository.adminRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
@Service
public class adminServiceImpl implements adminService {
    private final adminRepository adminRepository;
    @Autowired
    public adminServiceImpl(adminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

}
