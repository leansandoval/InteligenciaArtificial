package utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Helper para crear la KieSession a partir del kmodule.xml
 * del classpath (src/main/resources/META-INF/kmodule.xml).
 */
public class KnowledgeSessionHelper {

    /**
     * Carga el KieContainer desde el classpath (kmodule.xml + DRL).
     * Se llama una sola vez por clase de test (@BeforeAll).
     */
    public static KieContainer createRuleBase() {
        KieServices kieServices = KieServices.Factory.get();
        return kieServices.getKieClasspathContainer();
    }

    /**
     * Abre una KieSession stateful con el nombre indicado en kmodule.xml.
     * Agrega un listener que imprime por consola cada regla disparada.
     */
    public static KieSession getStatefulKnowledgeSessionWithCallback(
            KieContainer kieContainer, String sessionName) {

        KieSession session = kieContainer.newKieSession(sessionName);

        // Listener que imprime cada regla que se dispara
        session.addEventListener(new org.kie.api.event.rule.DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(org.kie.api.event.rule.AfterMatchFiredEvent event) {
                System.out.println("  [Regla disparada] " + event.getMatch().getRule().getName());
            }
        });

        return session;
    }
}

