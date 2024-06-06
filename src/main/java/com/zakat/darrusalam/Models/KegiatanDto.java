package com.zakat.darrusalam.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class KegiatanDto {

    private String namaKegiatan;
    private String deskripsiKegiatan;
    private MultipartFile ImageFile;

}
