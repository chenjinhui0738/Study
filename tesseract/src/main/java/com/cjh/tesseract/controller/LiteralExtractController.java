package com.cjh.tesseract.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class LiteralExtractController {

    @PostMapping("/image/extract")
    public String reg(@RequestParam("file") MultipartFile file) throws IOException {
        String result = "";
        String filename = file.getOriginalFilename();
        File save = new File(System.getProperty("user.dir")+"\\tesseract\\src\\main\\resources\\pic\\"+filename);
        if (!save.exists()){
            save.createNewFile();
        }
        file.transferTo(save);
        String cmd = String.format("tesseract %s stdout -l %s",System.getProperty("user.dir")+"\\tesseract\\src\\main\\resources\\pic\\"+filename,"chi_sim");
        result = cmd(cmd);
        return result;
    }
    @RequestMapping("/test")
    public String test(String name) throws IOException {
        String result = "";
        return name;
    }

    public static String cmd(String cmd) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            System.out.println(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
