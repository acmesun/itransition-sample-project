package by.lukyanets.acmesun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfig {

    @Bean
    public TwitterFactory getTwitter(
            @Value("${twitter.key}") String key,
            @Value("${twitter.secret}") String secret
    ) {
        var builder = new ConfigurationBuilder()
                .setOAuthConsumerKey(key)
                .setOAuthConsumerSecret(secret)
                .setIncludeEmailEnabled(true);
        return new TwitterFactory(builder.build());
    }
}
