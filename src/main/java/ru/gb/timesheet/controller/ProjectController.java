package ru.gb.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;

    private final TimesheetService timesheetService;

    public ProjectController(ProjectService service, TimesheetService timesheetService) {
        this.service = service;
        this.timesheetService = timesheetService;
    }

    // /timesheets/{id}
    @GetMapping("/{id}") // получить все
    public ResponseEntity<Project> get(@PathVariable Long id) {
        Optional<Project> ts = service.getById(id);

        if (ts.isPresent()) {
//      return ResponseEntity.ok().body(ts.get());
            return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping // получить все
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = service.create(project);

        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        // 204 No Content
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project) {
        service.update(project);

        // 204 No Content
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllByProject(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetService.getAllByProject(id));
    }
}
