package backward;

import static utils.TestCaseUtils.assertResults;
import static utils.TestCaseUtils.print;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.FactHandle;

import model.AccionRecomendada;
import model.Arranque;
import model.Automovil;
import model.Bateria;
import model.Fuerza;
import model.Motor;
import model.Recomendacion;
import model.TanqueCombustible;
import utils.KnowledgeSessionHelper;

class BackwardTestCases {

	// Debe coincidir con el nombre de ksession en META-INF/kmodule.xml
	private static final String K_SESSION_NAME = "kbackward-chaining-session";

	private KieSession sessionStatefull;
	private static KieContainer kieContainer;

	FactHandle automovilDir;

	@BeforeAll
	static void beforeallTestSetup() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	private void prepareKnowledgeSession() {
		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer, K_SESSION_NAME);
		Agenda agenda = sessionStatefull.getAgenda();
		// El ultimo setFocus queda activo primero; se apilan de ultimo a primero
		agenda.getAgendaGroup("Prescripcion de falla de arranque de automovil").setFocus();
		agenda.getAgendaGroup("Diagnostico motor").setFocus();
		agenda.getAgendaGroup("Diagnostico preliminar").setFocus();
		agenda.getAgendaGroup("Diagnostico Tanque de combustible").setFocus();
		agenda.getAgendaGroup("Diagnostico Bateria").setFocus();
	}

	@BeforeEach
	void setUp() {
		print("----------Start----------");
		print();
		this.prepareKnowledgeSession();
	}

	@AfterEach
	void tearDown() {
		if (sessionStatefull != null) {
			sessionStatefull.dispose();
		}
		print();
		print("----------End----------");
		print();
	}

	@Test
	void cargarBateriaTest() {
		print("Caso de prueba: Cargar Bateria");

		Motor motor = new Motor();
		motor.setArranque(Arranque.NoGira);

		Bateria bateria = new Bateria();
		bateria.setCargaPorcentual(1.0);

		TanqueCombustible tanque = new TanqueCombustible();
		tanque.setCargaLts(20.0);

		Automovil automovil = new Automovil(motor, bateria, tanque);
		print(automovil);
		sessionStatefull.insert(automovil);
		sessionStatefull.fireAllRules();

		Recomendacion recomendacion = automovil.getRecomendacion();
		AccionRecomendada valorEsperado = AccionRecomendada.CargarBateria;
		assertResults(recomendacion, valorEsperado);
	}

	@Test
	void necesitaCombustibleTest() {
		print("Caso de prueba: Cargar Combustible");

		Motor motor = new Motor();
		motor.setArranque(Arranque.Gira);

		TanqueCombustible tanque = new TanqueCombustible();
		tanque.setCargaLts(0.0);

		Bateria bateria = new Bateria();
		bateria.setCargaPorcentual(70.0);

		Automovil automovil = new Automovil(motor, bateria, tanque);
		print(automovil);
		sessionStatefull.insert(automovil);
		sessionStatefull.fireAllRules();

		Recomendacion recomendacion = automovil.getRecomendacion();
		AccionRecomendada valorEsperado = AccionRecomendada.CargarCombustible;
		assertResults(recomendacion, valorEsperado);
	}

	@Test
	void limpiarTuberiasCombustibleTest() {
		print("Caso de prueba: Limpiar Tuberias de combustible");

		Motor motor = new Motor();
		motor.setArranque(Arranque.Gira);
		motor.setFuerza(Fuerza.Debil);

		Bateria bateria = new Bateria();
		bateria.setCargaPorcentual(80.0);

		TanqueCombustible tanque = new TanqueCombustible();
		tanque.setCargaLts(40.0);

		Automovil automovil = new Automovil(motor, bateria, tanque);
		print(automovil);
		sessionStatefull.insert(automovil);
		sessionStatefull.fireAllRules();

		Recomendacion recomendacion = automovil.getRecomendacion();
		AccionRecomendada valorEsperado = AccionRecomendada.LimpiarTuberiasCombustible;
		assertResults(recomendacion, valorEsperado);
	}

	@Test
	void ajustarPuntosDeInjeccionTest() {
		print("Caso de prueba: Ajustar Puntos de Injeccion");

		Motor motor = new Motor();
		motor.setArranque(Arranque.Gira);
		motor.setPresentaExplosiones(true);

		Bateria bateria = new Bateria();
		bateria.setCargaPorcentual(20.0);

		TanqueCombustible tanque = new TanqueCombustible();
		tanque.setCargaLts(40.0);

		Automovil automovil = new Automovil(motor, bateria, tanque);
		print(automovil);
		sessionStatefull.insert(automovil);
		sessionStatefull.fireAllRules();

		Recomendacion recomendacion = automovil.getRecomendacion();
		AccionRecomendada valorEsperado = AccionRecomendada.AjustarPuntosDeInyeccion;
		assertResults(recomendacion, valorEsperado);
	}

	@Test
	void ajustarTimingMotorTest() {
		print("Caso de prueba: Ajustar Timing del Motor");

		Motor motor = new Motor();
		motor.setArranque(Arranque.Gira);
		motor.setPresentaGolpes(true);

		Bateria bateria = new Bateria();
		bateria.setCargaPorcentual(60.0);

		TanqueCombustible tanque = new TanqueCombustible();
		tanque.setCargaLts(60.0);

		Automovil automovil = new Automovil(motor, bateria, tanque);
		print(automovil);
		sessionStatefull.insert(automovil);
		sessionStatefull.fireAllRules();

		Recomendacion recomendacion = automovil.getRecomendacion();
		AccionRecomendada valorEsperado = AccionRecomendada.AjustarTimingMotor;
		assertResults(recomendacion, valorEsperado);
	}
	
}
