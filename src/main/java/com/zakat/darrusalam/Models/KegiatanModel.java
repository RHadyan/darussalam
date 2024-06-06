package com.zakat.darrusalam.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter

@Entity
@Table(name = "tbKegiatan")
public class KegiatanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namaKegiatan;
    private String deskripsiKegiatan;
    private String ImageFileName;


}
