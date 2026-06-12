package com.example.inventory.config;

import com.nexacro.java.xapi.tx.PlatformType;
import com.nexacro.uiadapter.jakarta.core.context.ApplicationContextProvider;
import com.nexacro.uiadapter.jakarta.core.resolve.NexacroHandlerMethodReturnValueHandler;
import com.nexacro.uiadapter.jakarta.core.resolve.NexacroMappingExceptionResolver;
import com.nexacro.uiadapter.jakarta.core.resolve.NexacroMethodArgumentResolver;
import com.nexacro.uiadapter.jakarta.core.resolve.NexacroRequestMappingHandlerAdapter;
import com.nexacro.uiadapter.jakarta.core.view.NexacroFileView;
import com.nexacro.uiadapter.jakarta.core.view.NexacroView;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * Spring MVC의 기본 JSON 기반 요청/응답 처리 체계를 Nexacro Dataset/XML 기반 처리 체계로 교체하는 설정 클래스
 */
@Configuration
public class NexacroConfig extends WebConfig implements WebMvcRegistrations {

    /**
     * Spring Bean들을 Nexacro 내부에서도 참조할 수 있게 함
     */
    @Bean
    @Lazy(false)
    public ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

    /**
     * SpringAdapter를 NexacroAdapter로 교체
     * Spring MVC를 Nexacro용으로 교체
     */
    @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return new NexacroRequestMappingHandlerAdapter();
    }

    /**
     * Controller 파라미터 해석기 등록
     * Dataset --> Java 객체 변환기
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        NexacroMethodArgumentResolver nexacroResolver = new NexacroMethodArgumentResolver();
        resolvers.add(nexacroResolver);
        super.addArgumentResolvers(resolvers);
    }

    @Bean
    public NexacroView nexacroView() {
        NexacroView view = new NexacroView();
        view.setDefaultContentType(PlatformType.CONTENT_TYPE_XML);
        view.setDefaultCharset("UTF-8");
        return view;
    }

    /**
     * 응답 처리기
     * Java 객체 --> Dataset
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        NexacroHandlerMethodReturnValueHandler returnValueHandler = new NexacroHandlerMethodReturnValueHandler();

        // 실제 응답 생성기
//        NexacroView nexacroView = new NexacroView();
//        nexacroView.setDefaultContentType(PlatformType.CONTENT_TYPE_XML);
//        nexacroView.setDefaultCharset("UTF-8");
        returnValueHandler.setView(nexacroView());

        // 파일 다운로드 전용
        NexacroFileView nexacroFileView = new NexacroFileView();
        returnValueHandler.setFileView(nexacroFileView);

        handlers.add(returnValueHandler);

        super.addReturnValueHandlers(handlers);
    }

    /**
     * 넥사크로플랫폼 에러 처리 ExceptionResolver 등록
     * Exception → Nexacro 에러 응답
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

//        NexacroView nexacroView = new NexacroView();
//        nexacroView.setDefaultContentType(PlatformType.CONTENT_TYPE_XML);
//        nexacroView.setDefaultCharset("UTF-8");

        NexacroMappingExceptionResolver nexacroException = new NexacroMappingExceptionResolver();

        nexacroException.setView(nexacroView());
        nexacroException.setShouldLogStackTrace(true);  // 서버 로그 출력
        nexacroException.setShouldSendStackTrace(true); // 클라이언트(Nexacro)에도 스택트레이스 전달
        nexacroException.setOrder(1);
        resolvers.add(nexacroException);

        super.configureHandlerExceptionResolvers(resolvers);
    }

}
