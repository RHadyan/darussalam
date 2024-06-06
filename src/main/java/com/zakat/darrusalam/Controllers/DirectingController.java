package com.zakat.darrusalam.Controllers;


import com.zakat.darrusalam.Models.GaleriModel;
import com.zakat.darrusalam.Models.KegiatanModel;
import com.zakat.darrusalam.Repository.BerandaRepository;
import com.zakat.darrusalam.Repository.GaleriRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class DirectingController {

    @Autowired
    private BerandaRepository berandarpo;

    @Autowired
    private GaleriRepository galeriRepo;

    @GetMapping("/index")
    public String index(Model model){

        List<KegiatanModel> kegiatan = berandarpo.findAll();
        model.addAttribute("kegiatans", kegiatan);

        List<GaleriModel> galeri = galeriRepo.findAll();
        model.addAttribute("galeris", galeri);

        return "user/index";
    }

    @GetMapping("/tentang_kami")
    public String tentangKami(){
        return "user/tentang_kami";
    }

    @GetMapping("/zakat-fitrah")
    public String zakatFitrah(){
        return "user/zakat-fitrah";
    }

    @GetMapping("/zakat-mal")
    public String zakatMal(){
        return "user/zakat-mal";
    }
    @GetMapping("/donasi")
    public String donasi(){
        return "user/donasi";
    }
    @GetMapping("/blog")
    public String blog(){
        return "user/blog";
    }
    @GetMapping("/kontak")
    public String kontak(){
        return "user/kontak";
    }

    @GetMapping("/")
    public String check(){
        return "user/paud";
    }
    @GetMapping("/form-zakat-fitrah")
    public String formZakatFitrah(){
        return "user/form-zakat-fitrah";
    }

    @GetMapping("/form-zakat-mal")
    public String formZakatMal(){
        return "user/form-zakat-mal";
    }

    @GetMapping("/form-donasi")
    public String formDonasi(){
        return "user/form-donasi";
    }

//    admmin


}
