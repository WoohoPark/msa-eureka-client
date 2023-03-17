# 로그인 인증 모듈 

---

#### Spring Security
Spring Security란?
애플리케이션의 보안 인증과 인가를 제공하는 프레임워크이다.
Spring Security는 '인증'과 '인가'에 대한 부분을 Filter 흐름에 따라 처리하고 있다.
Filter는 Dispatcher Servlet으로 가기 전에 적용되므로 가장 먼저 URL 요청을 받지만,
보안과 관련해서 체계적으로 많은 옵션을 제공해주기 때문에 개발자 입장에서는 일일이 보안관련 로직을 작성하지 않아도 된다는 장점이 있다.

인증과 인가
인증(Authentication): 해당 사용자가 본인이 맞는지를 확인하는 절차
인가(Authorization): 인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차
Spring Security에서는 이러한 인증과 인가를 위해 Principal을 아이디로, Credential을 비밀번호로 사용하는 Credential 기반의 인증 방식을 사용한다.

Spring Security 요청을 어떻게 처리할까요?


Spring Security 는 서블릿필터 체인을 자동으로 구성하고 요청을 거치게 합니다.
기본적으로 위와 같이 사용하는 필터들이 많이 있으며 그래서 우리는 필요한 필터들 만을 그 안에서 사용합니다.
(이를 Filter들은 여러개가 연결되어 있어 Filter chain이라고도 불린다.)

Spring Security Filter 종류
HeaderWriterFilter : Request의 Http 해더를 검사하여 header를 추가하거나 빼주는 역할을 한다.
CorsFilter : 허가된 사이트나 클라이언트의 요청인지 검사하는 역할을 한다.
CsrfFilter : Post나 Put과 같이 리소스를 변경하는 요청의 경우 내가 내보냈던 리소스에서 올라온 요청인지 확인한다.
LogoutFilter : Request가 로그아웃하겠다고 하는것인지 체크한다.
UsernamePasswordAuthenticationFilter : username / password 로 로그인을 하려고 하는지 체크하여 승인이 되면 Authentication을 부여하고 이동 할 페이지로 이동한다.
ConcurrentSessionFilter : 동시 접속을 허용할지 체크한다.
BearerTokenAuthenticationFilter : Authorization 해더에 Bearer 토큰을 인증해주는 역할을 한다.
BasicAuthenticationFilter : Authorization 해더에 Basic 토큰을 인증해주는 역할을 한다.
RequestCacheAwareFilter : request한 내용을 다음에 필요할 수 있어서 Cache에 담아주는 역할을 한다. 다음 Request가 오면 이전의 Cache값을 줄 수 있다.
SecurityContextHolderAwareRequestFilter : 보안 관련 Servlet 3 스펙을 지원하기 위한 필터라고 한다.
RememberMeAuthenticationFilter : 아직 Authentication 인증이 안된 경우라면 RememberMe 쿠키를 검사해서 인증 처리해준다.
AnonymousAuthenticationFilter : 앞선 필터를 통해 인증이 아직도 안되었으면 해당 유저는 익명 사용자라고 Authentication을 정해주는 역할을 한다. (Authentication이 Null인 것을 방지!!)
SessionManagementFilter : 서버에서 지정한 세션정책에 맞게 사용자가 사용하고 있는지 검사하는 역할을 한다.
ExcpetionTranslationFilter : 해당 필터 이후에 인증이나 권한 예외가 발생하면 해당 필터가 처리를 해준다.
FilterSecurityInterceptor : 사용자가 요청한 request에 들어가고 결과를 리턴해도 되는 권한(Authorization)이 있는지를 체크한다. 해당 필터에서 권한이 없다는 결과가 나온다면 위의 - ExcpetionTranslationFilter필터에서 Exception을 처리해준다.
Spring Security 로그인 인증 구조


Spring Security Annotation


EanableGlobalMethodSecurity 을 통해서 어노테이션을 Controller에서 사용할 수 있는데.



위와 같이 요청전,요청후에 대한 인가를 명시할 수 있습니다.

마무리
Spring Security 아키텍처와 로그인 인증 구조에 대해 알아 보았습니다.
개인적으로 Security 아키텍처를 머리속에 넣어 두어야 소스 구현시 흐름과 이해가 쉽게 공부 된다고 생각 됩니다.
https://codevang.tistory.com/273 기본적인 Security 커스텀 마이징 한 내역들이 잘 정리 되어 있는 블로그를 소개 드립니다.
실제 소스 구현시 참고 하시면 도움이 될 것 같습니다.

출처
webfirewood.tistory.com/115
https://okky.kr/articles/382738
https://sjh836.tistory.com/165
https://velog.io/@bum12ark/MSA-JWT-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0-1.-%EB%A1%9C%EA%B7%B8%EC%9D%B8
https://dev-coco.tistory.com/174
https://debugdaldal.tistory.com/89
https://codevang.tistory.com/273
https://catsbi.oopy.io/c0a4f395-24b2-44e5-8eeb-275d19e2a536
https://www.youtube.com/watch?v=aEk-7RjBKwQ

profile




#### JPA+QueryDSL
