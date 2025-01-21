package com.lecosBurguer.apis.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Extract default authorities (e.g., from "scope")
        Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);

        // Extract roles from "realm_access.roles"
        List<String> realmRoles = getRolesFromClaim(jwt, "realm_access", "roles");

        // Extract roles from "resource_access.lecosBurguer.roles"
        List<String> resourceRoles = getRolesFromResourceAccess(jwt, "lecosBurguer");

        // Combine all roles
        List<String> allRoles = Stream.concat(realmRoles.stream(), resourceRoles.stream())
                .collect(Collectors.toList());

        // Add roles as GrantedAuthority
        authorities.addAll(allRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList()));

        return authorities;
    }

    private List<String> getRolesFromClaim(Jwt jwt, String claim, String subClaim) {
        Map<String, Object> claimMap = jwt.getClaimAsMap(claim);
        if (claimMap == null || !claimMap.containsKey(subClaim)) {
            return List.of();
        }
        return ((List<?>) claimMap.get(subClaim)).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private List<String> getRolesFromResourceAccess(Jwt jwt, String clientId) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey(clientId)) {
            return List.of();
        }
        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
        if (clientAccess == null || !clientAccess.containsKey("roles")) {
            return List.of();
        }
        return ((List<?>) clientAccess.get("roles")).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}

