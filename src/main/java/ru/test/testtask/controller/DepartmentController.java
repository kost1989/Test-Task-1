package ru.test.testtask.controller;

import org.springframework.web.bind.annotation.*;
import ru.test.testtask.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("department")
public class DepartmentController {

    private int counter = 4;

    private List<Map<String, String>> departmens = new ArrayList<>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "first department"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "second department"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "third department"); }});
    }};
    @GetMapping
    public List<Map<String, String>> list() {
        return departmens;
    }

    @GetMapping("{id}")
    public Map<String, String> getDepartmenFromId(@PathVariable String id) {
        return getDepartmentById(id);
    }

    private Map<String, String> getDepartmentById(String id) {
        return departmens.stream()
                .filter(departmens -> departmens.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> department) {
        department.put("id", String.valueOf(counter++));

        departmens.add(department);

        return department;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> department) {
        Map<String, String> departmentFromDB = getDepartmentById(id);

        departmentFromDB.putAll(department);
        departmentFromDB.put("id", id);

        return departmentFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> department = getDepartmentById(id);
        departmens.remove(department);
    }
}
