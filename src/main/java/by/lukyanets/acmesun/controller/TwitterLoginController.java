package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.user.UserRegistrationTwitterDto;
import by.lukyanets.acmesun.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class TwitterLoginController {
    private static final String REQUEST_TOKEN_ATTR = "requestToken";
    private static final String TWITTER_ATTR = "twitter";

    private final UserService service;
    private final TwitterFactory twitterFactory;
    private final String twitterUrl;

    public TwitterLoginController(UserService service,
                                  TwitterFactory twitterFactory,
                                  @Value("${twitter.callback-url}") String twitterUrl) {
        this.service = service;
        this.twitterFactory = twitterFactory;
        this.twitterUrl = twitterUrl;
    }

    @SneakyThrows
    @GetMapping("/twitter/token")
    public RedirectView getToken(HttpServletRequest request) {
        var twitter = twitterFactory.getInstance();
        RequestToken requestToken = twitter.getOAuthRequestToken(twitterUrl);
        request.getSession().setAttribute(REQUEST_TOKEN_ATTR, requestToken);
        request.getSession().setAttribute(TWITTER_ATTR, twitter);
        return new RedirectView(requestToken.getAuthorizationURL());
    }

    @SneakyThrows
    @GetMapping("/twitter/callback")
    public String twitterCallback(@RequestParam(value = "oauth_verifier", required = false) String oauthVerifier,
                                  @RequestParam(value = "denied", required = false) String denied,
                                  HttpServletRequest request) {

        if (denied != null) {
            return "redirect:/login";
        }
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute(REQUEST_TOKEN_ATTR);
        Twitter twitter = (Twitter) request.getSession().getAttribute(TWITTER_ATTR);
        // Generate access token only to fill user data in twitter entity
        twitter.getOAuthAccessToken(requestToken, oauthVerifier);
        request.getSession().removeAttribute(REQUEST_TOKEN_ATTR);
        service.registerNewAccount(new UserRegistrationTwitterDto(
                twitter.getScreenName(),
                twitter.verifyCredentials().getEmail(),
                null,
                null
        ));

        return "redirect:/user";
    }
}

