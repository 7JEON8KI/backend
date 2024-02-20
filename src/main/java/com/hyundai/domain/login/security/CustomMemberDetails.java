package com.hyundai.domain.login.security;

import com.hyundai.domain.login.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * fileName      : CustomMemberDetails
 * author        : 변형준
 * since         : 2/20/24
 * 내용           :
 */
@Getter
@Setter
public class CustomMemberDetails implements UserDetails {
    private String memberId;
    private String memberEmail;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomMemberDetails(String memberId, String memberEmail, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.authorities = authorities;
    }

    public static CustomMemberDetails create(Member member) {
        List<SimpleGrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(member.getMemberRole().toString()));

        return new CustomMemberDetails(member.getMemberId(),
                member.getMemberEmail(),
                authorities);
    }

    public static CustomMemberDetails create(Member member, Map<String, Object> attributes) {
        CustomMemberDetails userDetails = CustomMemberDetails.create(member);
        userDetails.setAttributes(attributes);
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
