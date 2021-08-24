package br.com.nhw.std.artes.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.nhw.std.artes.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.nhw.std.artes.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.nhw.std.artes.domain.User.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Authority.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.nhw.std.artes.domain.Ambiente.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.AreaDepto.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.AreaDepto.class.getName() + ".contatoes");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".contatoes");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".artistas");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".empresas");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName() + ".seguros");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName() + ".artistas");
            createCache(cm, br.com.nhw.std.artes.domain.AcervoAtual.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.AcervoAtual.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.AndarMapa.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.AndarMapa.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Artista.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Artista.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Artista.class.getName() + ".contatoes");
            createCache(cm, br.com.nhw.std.artes.domain.Categoria.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Categoria.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.DadoDocumental.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.DadoDocumental.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Data.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Data.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Empresa.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Empresa.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Espaco.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Espaco.class.getName() + ".andarMapas");
            createCache(cm, br.com.nhw.std.artes.domain.FuncaoArtista.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.FuncaoArtista.class.getName() + ".artistas");
            createCache(cm, br.com.nhw.std.artes.domain.Moeda.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Moeda.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Moeda.class.getName() + ".seguros");
            createCache(cm, br.com.nhw.std.artes.domain.Nivel.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Nivel.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Obra.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Obra.class.getName() + ".dadoDocumentals");
            createCache(cm, br.com.nhw.std.artes.domain.Responsavel.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Responsavel.class.getName() + ".respVerbetes");
            createCache(cm, br.com.nhw.std.artes.domain.Responsavel.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Seguro.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Seguro.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.Tecnica.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.Tecnica.class.getName() + ".obras");
            createCache(cm, br.com.nhw.std.artes.domain.TipoDocumento.class.getName());
            createCache(cm, br.com.nhw.std.artes.domain.TipoDocumento.class.getName() + ".dadoDocumentals");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".artistaNascidos");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".artistaFalescidos");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName() + ".seguroSegs");
            createCache(cm, br.com.nhw.std.artes.domain.Contato.class.getName() + ".seguroCors");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".artistaNascs");
            createCache(cm, br.com.nhw.std.artes.domain.Cidade.class.getName() + ".artistaFalescs");
            createCache(cm, br.com.nhw.std.artes.domain.Responsavel.class.getName() + ".artVerbetes");
            createCache(cm, br.com.nhw.std.artes.domain.Artista.class.getName() + ".responsavelLegals");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
