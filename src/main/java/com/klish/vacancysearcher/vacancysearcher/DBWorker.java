package com.klish.vacancysearcher.vacancysearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DBWorker {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private RequestRepository requestRepository;

    public HashMap<String, List<String>> getUserRequests(String username) {
        int user_id;
        if (userRepository.checkIfExists(username) == 1) {
            user_id = userRepository.getId(username);
        } else {
            return new HashMap<>();
        }

        HashMap<String, List<String>> date_map = new HashMap<>();

        List<Request> requests = requestRepository.getUserRequests(user_id);
        for (Request req : requests) {
            Skill skill = req.getSkill();
            String string_skill = skill.getName() + " (" + skill.getOccurrence_number() + ") | " + skill.getRegion();

            String date = req.getDate();

            if (!date_map.containsKey(date)) {
                List<String> new_list = new ArrayList<>();
                new_list.add(string_skill);
                date_map.put(date, new_list);
            } else {
                date_map.get(date).add(string_skill);
            }
        }
        return date_map;
    }

    public void writeToDB(String username, String region, String vacancy, int count) throws Exception {

        Counter<String> skills = HHVacancyWorker.getKeySkills(region, vacancy);
        skills.sort();
        List<String> skill_names = skills.getKeys();
        List<Integer> skill_counts = skills.getValues();
        if (skill_names.size() > count) {
            skill_names = skill_names.subList(0, count);
            skill_counts = skill_counts.subList(0, count);
        }
        int length = skill_names.size();

        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String curr_datetime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(datetime);

        User user_for_request;
        boolean user_is_new = false;

        if (userRepository.checkIfExists(username) == 1) {
            int user_id = userRepository.getId(username);
            user_for_request = userRepository.getOne(user_id);
        } else {
            user_is_new = true;
            User new_user = new User();
            new_user.setLogin(username);

            userRepository.saveAndFlush(new_user);
            user_for_request = new_user;
        }

        for(int i = 0; i < length; i++) {
            String skill_name = skill_names.get(i);
            int skill_count = skill_counts.get(i);

            Skill skill_for_request;
            boolean skill_is_new = false;

            if (skillRepository.checkIfExists(skill_name, region, skill_count) == 1) {
                int skill_id = skillRepository.getId(skill_name, region, skill_count);
                skill_for_request = skillRepository.getOne(skill_id);
            } else {
                skill_is_new = true;
                Skill new_skill = new Skill();
                new_skill.setName(skill_name);
                new_skill.setRegion(region);
                new_skill.setOccurrence_number(skill_count);

                skillRepository.saveAndFlush(new_skill);
                skill_for_request = new_skill;
            }

            if (user_is_new || skill_is_new){
                Request new_request = new Request();
                new_request.setUser(user_for_request);
                new_request.setSkill(skill_for_request);
                new_request.setDate(curr_datetime);
                requestRepository.saveAndFlush(new_request);
            }
        }
    }
}
