package sc.player2018;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sc.player2018.logic.RandomLogic;
import sc.plugin2018.AbstractClient;
import sc.plugin2018.IGameHandler;
import de.oltmannsdaniel.hui.MasterLogic;

/**
 * Erlaubt es verschiedene Logiken zu verwenden und eine davon auszuwählen und
 * Instanzen dieser Logik zu erzeugen
 * 
 * @author and
 */
public enum LogicFactory {
	// Verfügbare Taktiken (Implementierungen des IGameHandler) müssen hier
	// eingetragen wie im Beispiel eingetragen und ihre Klasse angegeben werden
	RANDOM(RandomLogic.class),

	// MasterLogic von uns
	MASTER(MasterLogic.class),

	// Die Logik die gewählt wird, wenn kein passender Eintrag zu der Eingabe
	// gefunden wurde:
	DEFAULT(MasterLogic.class);

	private Class<? extends IGameHandler> logic;
	private static final Logger	logger = LoggerFactory
			.getLogger(LogicFactory.class);

	private LogicFactory(Class<? extends IGameHandler> chosenLogic) {
		logic = chosenLogic;
	}

	/**
	 * Erstellt eine Logik-Instanz und gibt diese zurück
	 * 
	 * @param client
	 *            Der aktuelle Client
	 * @return Eine Instanz der gewaehlten Logik
	 * @throws Exception
	 *             Wenn etwas schief gelaufen ist und keine Instanz erstellt
	 *             werden konnte, wird eine Exception geworfen!
	 */
	public IGameHandler getInstance(AbstractClient client) throws Exception {
		logger.debug("Erzeuge Instanz von: {}", name());
		return (IGameHandler) logic.getConstructor(client.getClass())
				.newInstance(client);
	}

}
