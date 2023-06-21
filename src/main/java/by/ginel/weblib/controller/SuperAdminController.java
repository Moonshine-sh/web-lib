package by.ginel.weblib.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/super")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SUPERADMIN')")
public class SuperAdminController {
}
