package com.zakat.darrusalam.Controllers;


import com.zakat.darrusalam.Models.GaleriDto;
import com.zakat.darrusalam.Models.GaleriModel;
import com.zakat.darrusalam.Models.KegiatanDto;
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

@Controller
@RequestMapping("/admin")
public class BerandaController {


    @Autowired
    private BerandaRepository berandaRepo;

    @Autowired
    private GaleriRepository galeriRepo;

    @GetMapping({"","/"})
    public String admin(Model model){
        List<KegiatanModel> kegiatan = berandaRepo.findAll();
        model.addAttribute("kegiatans", kegiatan);

        List<GaleriModel> galeri = galeriRepo.findAll();
        model.addAttribute("galeris", galeri);

        KegiatanDto kegiatanDto = new KegiatanDto();
        model.addAttribute("kegiatanDto", kegiatanDto);

        GaleriDto galeriDto = new GaleriDto();
        model.addAttribute("galeriDto", galeriDto);


        return "Admin/beranda";
    }



    @GetMapping("deleteGaleri/{id}")
    public String deleteGaleri(@PathVariable int id){

        try {
            GaleriModel galeri = galeriRepo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + galeri.getImageFileName());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());
            }

            galeriRepo.delete(galeri);

        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());


        }
        return "redirect:/admin";
    }

    @PostMapping("/createGaleri")
    public String CreateGaleri(
            @Valid @ModelAttribute GaleriDto galeriDto){
//        add image
        MultipartFile image = galeriDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        }catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }

        GaleriModel galeri = new GaleriModel();
        galeri.setImageFileName(storageFileName);

        galeriRepo.save(galeri);

        return "redirect:/admin";
    }
    @PostMapping("/updateGaleri")
    public String updateGaleri(
            @RequestParam int id,
            @RequestParam("imageFileName") MultipartFile imageFileName) {
        try {
            GaleriModel galeri = galeriRepo.findById(id).orElseThrow(() -> new RuntimeException("Kegiatan not found"));

            // Jika ada file gambar baru, simpan file baru dan update nama file
            if (!imageFileName.isEmpty()) {
                String uploadDir = "public/images/";
                String storageFileName = new Date().getTime() + "_" + imageFileName.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = imageFileName.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                    // Hapus file lama
                    Path oldImagePath = Paths.get(uploadDir + galeri.getImageFileName());
                    Files.deleteIfExists(oldImagePath);
                    // Set nama file baru
                    galeri.setImageFileName(storageFileName);
                }
            }

            galeriRepo.save(galeri);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/admin";
    }



    @PostMapping("/create")
    public String CreateFasilitas(
            @Valid @ModelAttribute KegiatanDto kegiatanDto,
            BindingResult result){

        if (kegiatanDto.getImageFile().isEmpty()){
            result.addError(new FieldError("kegiatanDto","imageFile","the image file is missing"));
        }
        if (result.hasErrors()){
            return  "redirect:/";
        }
//        add image
        MultipartFile image = kegiatanDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        }catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }

        KegiatanModel kegiatan = new KegiatanModel();
        kegiatan.setNamaKegiatan(kegiatanDto.getNamaKegiatan());
        kegiatan.setDeskripsiKegiatan(kegiatanDto.getNamaKegiatan());

        kegiatan.setImageFileName(storageFileName);

        berandaRepo.save(kegiatan);

        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateFasilitas(
            @RequestParam int id,
            @RequestParam String nameFasilitas,
            @RequestParam String deskripsi,

            @RequestParam("imageFileName") MultipartFile imageFileName) {
        System.out.println("ID: " + id);
        System.out.println("Name: " + nameFasilitas);
        System.out.println("Image File: " + imageFileName.getOriginalFilename());
        try {
            KegiatanModel kegiatan = berandaRepo.findById(id).orElseThrow(() -> new RuntimeException("Kegiatan not found"));

            // Update nama fasilitas
            kegiatan.setNamaKegiatan(nameFasilitas);
            kegiatan.setDeskripsiKegiatan(deskripsi);

            // Jika ada file gambar baru, simpan file baru dan update nama file
            if (!imageFileName.isEmpty()) {
                String uploadDir = "public/images/";
                String storageFileName = new Date().getTime() + "_" + imageFileName.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = imageFileName.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                    // Hapus file lama
                    Path oldImagePath = Paths.get(uploadDir + kegiatan.getImageFileName());
                    Files.deleteIfExists(oldImagePath);
                    // Set nama file baru
                    kegiatan.setImageFileName(storageFileName);
                }
            }

            berandaRepo.save(kegiatan);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable int id){

        try {
            KegiatanModel kegiatan = berandaRepo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + kegiatan.getImageFileName());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());
            }

            berandaRepo.delete(kegiatan);

        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());


        }
        return "redirect:/admin";
    }

//    galeri



}
