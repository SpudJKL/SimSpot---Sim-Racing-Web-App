package com.SimSpot.ecommerce.config;

import com.SimSpot.ecommerce.entity.Country;
import com.SimSpot.ecommerce.entity.County;
import com.SimSpot.ecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class configures the Spring REST endpoints by implementing Springs
 * RepositoryRestConfigurer. We override these methods and set up an EntityManager
 */
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    /**
     *
     * @param config Main configuration bean.
     * @param cors CORS configuration.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
//        disableHttpMethods(Product.class, config, theUnsupportedActions);
        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
//        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        // disable HTTP methods for Country: PUT, POST, DELETE and PATCH
        disableHttpMethods(Country.class, config, theUnsupportedActions);
        // disable HTTP methods for County: PUT, POST, DELETE and PATCH
        disableHttpMethods(County.class, config, theUnsupportedActions);
        // disable HTTP methods for Order: PUT, POST, DELETE and PATCH
        disableHttpMethods(Order.class, config, theUnsupportedActions);
        // call an internal helper method for exposing ids
        exposedIds(config);
        // cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    /**
     *
     * @param theClass
     * @param config
     * @param theUnsupportedActions
     */
    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    /**
     *
     * @param config
     */
    private void exposedIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // Create entity array of entity types
        List<Class> entityClasses = new ArrayList<>();

        // Get entity type for entities
        for (EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }
        // Expose IDs
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}