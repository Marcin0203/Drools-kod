package com.example.firstdrools;

import org.drools.core.marshalling.impl.ProtobufMessages;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDrools {

    private final KieContainer kieContainer;

    @Autowired
    public ServiceDrools(KieContainer kieContainer){
        this.kieContainer = kieContainer;
    }

    public Product getProductDiscout (Product product){
        String str ="";
        str +="package com.example.firstdrools; \n";
        str +="rule \"Offer for Diamond\" \n";
        str +="when \n";
        str +="productObject: Product(type==\"diamond\") \n";
        str +="then \n";
        str +="productObject.setDiscount(15000); \n";
        str +="end \n";
        str +="rule \"Offer for Gold\" \n";
        str +="when \n";
        str +="productObject: Product(type==\"gold\") \n";
        str +="then \n";
        str +="productObject.setDiscount(5); \n";
        str +="end \n";
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        knowledgeBuilder.add(ResourceFactory.newByteArrayResource(str.getBytes() ), ResourceType.DRL);

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

        kbase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());

        KieSession ksession = (StatefulKnowledgeSession) kbase.newKieSession();

        ksession.insert(product);
        ksession.fireAllRules();
        ksession.dispose();
        return product;
    }
}
