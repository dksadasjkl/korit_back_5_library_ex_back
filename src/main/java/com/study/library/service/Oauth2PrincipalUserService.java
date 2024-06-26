package com.study.library.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Oauth2PrincipalUserService implements OAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService(); // 기본값
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest); // super(요청 시 객체) 동일
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String provider = userRequest.getClientRegistration().getClientName(); // Google, Kakao, Naver
        Map<String, Object> newAttributes = null;
        String id = null;
        switch (provider) {
            case "Google":
                id = attributes.get("sub").toString();
                break;
            case "Naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                id = response.get("id").toString();
                break;
            case "Kakao":
                id = attributes.get("id").toString();
                break;
        }
        newAttributes = Map.of("id", id, "provider", provider);
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), newAttributes, "id"); // "id" 키값 getName
    }
}
