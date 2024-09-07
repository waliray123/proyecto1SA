package com.eatsleep.user.security;

import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbEntity;
import com.eatsleep.user.client.infrastructure.outputadapters.db.ClientDbEntityRepository;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbEntity;
import com.eatsleep.user.employee.infrastructure.outputadapters.db.EmployeeDbEntityRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private EmployeeDbEntityRepository employeeRepository;
    private ClientDbEntityRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(EmployeeDbEntityRepository employeeRepository, ClientDbEntityRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String username = "";
        String pass = "";
        Role role = Role.CUSTOMER;
        
        Optional<EmployeeDbEntity> employeeOpt = employeeRepository.findByEmail(email);
        
        if(employeeOpt.isPresent()){
            username = employeeOpt.get().getEmail();
            pass = employeeOpt.get().getPassword();
            role = employeeOpt.get().getRole();
        }else{
            ClientDbEntity client = clientRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            username = client.getEmail();
                pass = client.getPassword();
                role = client.getRole();
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(pass)
                .roles(role.toString())
                .build();

        return userDetails;

    }
}
