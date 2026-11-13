package com.exp71.rbac.controller;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DashboardController {
    @GetMapping("/me") public Map<String,String> me(Authentication auth) {
        String role=auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        return Map.of("username",auth.getName(),"role",role,"message","Authenticated successfully with JWT");
    }
    @GetMapping("/viewer")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR','VIEWER')")
    public Map<String,Object> viewer(){ return Map.of("status","ACCESS GRANTED","page","VIEWER","description","Read-only access to application information.","features",new String[]{"View published data","Browse information","Read reports"}); }
    @GetMapping("/editor")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public Map<String,Object> editor(){ return Map.of("status","ACCESS GRANTED","page","EDITOR","description","Create and update content. Administrative operations remain restricted.","features",new String[]{"Create content","Edit records","Preview changes"}); }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String,Object> admin(){ return Map.of("status","ACCESS GRANTED","page","ADMIN","description","Full system access for administration.","features",new String[]{"Manage users","Assign roles","View system activity","Access all resources"}); }
}
