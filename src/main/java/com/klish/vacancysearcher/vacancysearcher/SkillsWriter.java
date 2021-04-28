package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class SkillsWriter {

    public void writeSkills(String username, String region, String vacancy) throws Exception {

        String filename = String.format("tempfiles/%s.txt", username);

        ArrayList<String> key_skills = HHVacancyWorker.getKeySkills(region, vacancy);

        BufferedWriter writer = null;

        writer = new BufferedWriter(new FileWriter(filename));
        int i = 0;
        for (String skill : key_skills) {
            if (i == 10) {
                break;
            }
            writer.write(skill + "\n");
            i++;
        }
        writer.flush();
        writer.close();
    }

}
