package com.gmail.zariust.otherdrops.parameters.conditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.ConfigurationNode;
import com.gmail.zariust.otherdrops.Log;
import com.gmail.zariust.otherdrops.event.CustomDrop;
import com.gmail.zariust.otherdrops.event.OccurredEvent;
import com.gmail.zariust.otherdrops.parameters.Condition;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory.Default;

/**
 * @author Bane
 * 
 */
public class HealthCheck extends Condition {

	String name = "HealthCheck";

	private Boolean PlayerisEnabled;
	private Double PlayerHealth;
	private List<token> PlayerTokens;
	private Boolean VictimisEnabled;
	private Double VictimHealth;
	private List<token> VictimTokens;

	enum HealthCheckType {
		Player_HealthCheck, Victim_HealthCheck
	};

	public HealthCheck(String PlayerTokenString, String VictimTokenString) {

		// Parse String.. Player.Health: = 10.0
		// Parse String.. Player.Health: < 10.0
		// Parse String.. Player.Health: > 10.0
		// Parse String.. Player.Health: + 10.0 (Positive Value)
		// Parse String.. Player.Health: += 10.0 (Gain Health)
		// Parse String.. Player.Health: - 10.0 (Negative Value)
		// Parse String.. Player.Health: -= 10.0 (Loose Health)
		// Parse String.. Player.Health: 10.0
		// Parse String.. Player.Health: 10%

		Log.dMsg("HealthCheck():  PlayerTokens: " + PlayerTokenString);
		PlayerTokens = ParseConditionOperation(PlayerTokenString);

		Log.dMsg("HealthCheck():  VictimTokens: " + VictimTokenString);
		VictimTokens = ParseConditionOperation(VictimTokenString);

		PlayerisEnabled = false;
		VictimisEnabled = false;

		for (token aToken : PlayerTokens) {
			if (aToken != null) {
				PlayerisEnabled = true;

			}
		}

		for (token aToken : VictimTokens) {

			if (aToken != null) {
				VictimisEnabled = true;
			}
		}

		if (false == VictimisEnabled && false == PlayerisEnabled) {
			Log.dMsg("HealthCheck(): Error: Invalid Setting PlayerEnabled:"
					+ PlayerisEnabled + " VictimEnabled:" + VictimisEnabled);
		}

	}

	public boolean checkConditionToken(double playerHealth, token aToken,
			double playerMaxHealth) {

		if (aToken == null) // Error: Token is bad.
		{
			return false;
		}

		if (aToken.anCO == ConditionOperation.CO_ERROR) {
			return false;
		}

		Boolean checkPassed = false;

		Log.dMsg("HealthCheck:checkConditionToken() ConditionOperation: ("
				+ aToken.anCO.toString() + ")");

		Double aValue = aToken.Value;

		Log.dMsg("HealthCheck:checkConditionToken() aToken.Value: ("
				+ aToken.Value + ")");

		Log.dMsg("HealthCheck:checkConditionToken() isPercentage: ("
				+ aToken.isPercentage + ")");

		if (true == aToken.isPercentage) {
			aValue = playerMaxHealth * (aToken.Value * 0.01); // What is the
																// current
																// aValue.

		}

		Log.dMsg("HealthCheck:checkConditionToken() Player Health: ("
				+ playerHealth + ") Player Max Health (" + playerMaxHealth
				+ ") aValue (" + aValue + ")");

		switch (aToken.anCO) {
		case CO_N: // No special case.
		case CO_LToE: // No special case.
			if (playerHealth <= aValue) {
				checkPassed = true;
			}
			break;

		case CO_E:
			if (playerHealth == aValue) {
				checkPassed = true;
			}
			break;

		case CO_GT:
			if (playerHealth > aValue) {
				checkPassed = true;
			}
			break;

		case CO_GToE:
			if (playerHealth >= aValue) {
				checkPassed = true;
			}
			break;

		case CO_LT:
			if (playerHealth < aValue) {
				checkPassed = true;
			}
			break;

		case CO_M:
			if (playerHealth == -aValue) {
				checkPassed = true;
			}
			break;

		case CO_P:
			if (playerHealth == +aValue) {
				checkPassed = true;
			}
			break;

		case CO_ME: // Unimplemented (On Loss of aValue health)
			Log.dMsg("Unimplemented: - (On loss of health)");
			break;

		case CO_PE:
			Log.dMsg("Unimplemented: - (On addition of health)");
			break;

		default: // use default.
			Log.dMsg("Unimplemented: DEFAULT - Missing : ("
					+ aToken.anCO.toString() + ")");
			break;
		}

		Log.dMsg("HealthCheck:checkConditionToken() : checkPassed("
				+ checkPassed.toString() + ")");

		return checkPassed;
	}

	@Override
	public boolean checkInstance(CustomDrop drop, OccurredEvent occurrence) {
		Log.logInfo("Starting Health check !", Verbosity.HIGHEST);

		@SuppressWarnings("unused")
		Boolean VictimResultCorrect = false;
		Boolean PlayerResultCorrect = false;

		if (true == VictimisEnabled) {
			Log.logInfo(
					"HealthCheck:checkInstance() VictimHealthCheck: Enabled",
					Verbosity.HIGHEST);
			Player aVictim = occurrence.getPlayerVictim();

			for (token VictimToken : VictimTokens) {
				if (VictimToken == null) {
					Log.logInfo(
							"HealthCheck:checkInstance() VictimToken: Null",
							Verbosity.HIGHEST);
				} else {
					Log.logInfo(
							"HealthCheck:checkInstance() VictimToken: Value: "
									+ VictimToken.Value + " Op:"
									+ VictimToken.anCO.toString() + " P:"
									+ VictimToken.isPercentage,
							Verbosity.HIGHEST);
				}

				if (aVictim instanceof Player
						&& true == checkConditionToken(aVictim.getHealth(),
								VictimToken, aVictim.getMaxHealth())) {
					VictimResultCorrect = true;
				}

				else {
					Log.logInfo(
							"HealthCheck:checkInstance() VictimHealthCheck: Failed",
							Verbosity.HIGHEST);
					return false; // Check failed.
				}
			}
		}

		else {

			VictimResultCorrect = true; // Victim check is disabled, so we don't
										// care if the check passed / failed.
			Log.logInfo(
					"HealthCheck:checkInstance() VictimHealthCheck: Disabled",
					Verbosity.HIGHEST);
		}

		if (true == PlayerisEnabled) {
			Log.logInfo(
					"HealthCheck:checkInstance() PlayerHealthCheck: Enabled",
					Verbosity.HIGHEST);
			Player aPlayer = occurrence.getPlayerAttacker();

			for (token PlayerToken : PlayerTokens) {
				if (PlayerToken == null) {
					Log.logInfo(
							"HealthCheck:checkInstance() PlayerToken: Null",
							Verbosity.HIGHEST);
				} else {
					Log.logInfo(
							"HealthCheck:checkInstance() PlayerToken: Value: "
									+ PlayerToken.Value + " Op:"
									+ PlayerToken.anCO.toString() + " P:"
									+ PlayerToken.isPercentage,
							Verbosity.HIGHEST);
				}

				if (aPlayer instanceof Player
						&& true == checkConditionToken(aPlayer.getHealth(),
								PlayerToken, aPlayer.getMaxHealth())) {
					PlayerResultCorrect = true;
				} else {
					Log.logInfo(
							"HealthCheck:checkInstance() PlayerHealthCheck: Failed",
							Verbosity.HIGHEST);
					return false; // Check failed.
				}

			}
		} else {

			PlayerResultCorrect = true; // Player check is disabled, so we don't
										// care if the check passed / failed.
			Log.logInfo(
					"HealthCheck:checkInstance() PlayerHealthCheck: Disabled",
					Verbosity.HIGHEST);
		}

		Log.logInfo("HealthCheck:checkInstance() PlayerHealth Check: "
				+ PlayerResultCorrect.toString() + " VictimHealthCheck: "
				+ VictimResultCorrect.toString(), Verbosity.HIGHEST);

		if (VictimResultCorrect == true && PlayerResultCorrect == true) {
			Log.logInfo("HealthCheck:checkInstance() HealthCheck: Passed",
					Verbosity.HIGHEST);
			return true;
		} else {
			Log.logInfo("HealthCheck:checkInstance() HealthCheck: Failed",
					Verbosity.HIGHEST);
			return false; // default return.
		}
	}

	enum ConditionOperation {
		CO_N, CO_E, CO_LT, CO_GT, CO_LToE, CO_GToE, CO_P, CO_M, CO_PE, CO_ME, CO_CENT, CO_ERROR
	}; // "", =, <, >, <=, >=, +, -, +=, -=, %, Error

	public class token {
		public Double Value;
		public ConditionOperation anCO = ConditionOperation.CO_N;
		public boolean isPercentage = false;
	};

	private List<token> ParseConditionOperation(String input) {
		List<token> aList = new ArrayList<token>();

		token aToken = null;

		if (input == null) {
			// aToken.anCO = ConditionOperation.CO_ERROR;
			// return aToken;
			return aList;
		}

		Log.dMsg("ParseConditionOperation(): Start Parsing. Input (" + input
				+ ")");


		String patternString = "(?<op>[\\s\\+\\-<>=]+)*(?<number>[0-9\\.]+)+(?<percent>[%])*";

		Pattern p = Pattern.compile(patternString);
		Matcher m = p.matcher(input);

		while (m.find()) {
			aToken = new token();

			int count = m.groupCount();
			Log.dMsg("group count is " + count);
			Log.dMsg("ParseConditionOperation(): ops    ("
					+ m.group("op").toString() + ")");
			Log.dMsg("ParseConditionOperation(): number ("
					+ m.group("number").toString() + ")");

			char[] opgroup = m.group("op").toString().toCharArray();

			for (char s : opgroup) {
				Log.dMsg("ParseConditionOperation(): Parsing string (" + s
						+ ") [" + opgroup.length + "].");

				if (s == '+') {
					if (aToken.anCO == ConditionOperation.CO_E) // if we already
																// have a = then
																// make this +=
					{
						aToken.anCO = ConditionOperation.CO_PE;
					} else
						aToken.anCO = ConditionOperation.CO_P;

					continue;
				}

				if (s == '-') {
					if (aToken.anCO == ConditionOperation.CO_E) // if we already
																// have a = then
																// make this -=
					{
						aToken.anCO = ConditionOperation.CO_ME;
					} else
						aToken.anCO = ConditionOperation.CO_M;

					continue;
				}

				if (s == '<') {
					if (aToken.anCO == ConditionOperation.CO_E) // if we already
																// have a = then
																// make this <=
					{
						aToken.anCO = ConditionOperation.CO_LToE;
					} else
						aToken.anCO = ConditionOperation.CO_LT;

					continue;
				}

				if (s == '>') {
					if (aToken.anCO == ConditionOperation.CO_E) // if we already
																// have a = then
																// make this >=
					{
						aToken.anCO = ConditionOperation.CO_GToE;
					} else
						aToken.anCO = ConditionOperation.CO_GT;

					continue;
				}

				if (s == '=') {
					if (aToken.anCO == ConditionOperation.CO_LT) // if we
																	// already
																	// have a <
																	// then make
																	// this <=
					{
						aToken.anCO = ConditionOperation.CO_LToE;
					} else if (aToken.anCO == ConditionOperation.CO_GT) // if we
																		// already
																		// have
																		// a >
																		// then
																		// make
																		// this
																		// >=
					{
						aToken.anCO = ConditionOperation.CO_GToE;
					} else if (aToken.anCO == ConditionOperation.CO_M) // if we
																		// already
																		// have
																		// a -
																		// then
																		// make
																		// this
																		// -=
					{
						aToken.anCO = ConditionOperation.CO_ME;
					} else if (aToken.anCO == ConditionOperation.CO_P) // if we
																		// already
																		// have
																		// a +
																		// then
																		// make
																		// this
																		// +=
					{
						aToken.anCO = ConditionOperation.CO_PE;
					} else
						// otherwise set the operation to =
						aToken.anCO = ConditionOperation.CO_E;

					continue;
				}

				if (s == ' ') // ignore white spaces.
					continue;

			}

			if (m.group("percent") != null) // If we have a percentage sign
			{
				if (!m.group("percent").isEmpty()) {
					aToken.isPercentage = true;
				}
			}

			Log.dMsg("ParseConditionOperation(): Looking for a value.. Parsing string ("
					+ m.group("number").toString() + ") to a double!");

			// Otherwise we might have a value.. try parsing it.
			try {
				aToken.Value = Double.parseDouble(m.group("number").toString());
			} catch (Exception e) {
				Log.dMsg("ParseConditionOperation(): Error Parsing string ("
						+ m.group("number").toString() + ") to a double! :"
						+ e.getStackTrace());
				// return null;
			}

			aList.add(aToken);
		}

		Log.dMsg("ParseConditionOperation(): Returning List with ("
				+ aList.size() + ") elements!");

		return aList;

	}

	public static List<Condition> parse(ConfigurationNode node) {
		Log.dMsg("HealthCheck.parse(): Checking if Health check should be enabled! "
				+ node.toString());

		// Double PlayerHealthSetting = node.getDouble("player.health", null);
		String PlayerHealthSetting = node.getString("player.health", null);

		;

		Log.dMsg("HealthCheck.parse(): player.health=" + PlayerHealthSetting);

		// Double VictimHealthSetting = node.getDouble("victim.health", null);

		String VictimHealthSetting = node.getString("victim.health", null);

		Log.dMsg("HealthCheck.parse(): victim.health=" + VictimHealthSetting);

		List<Condition> conditionList = new ArrayList<Condition>();

		if (PlayerHealthSetting != null || VictimHealthSetting != null) {

			Log.logInfo(
					"HealthCheck.parse(): Adding Healthcheck to list of valid conditions!",
					Verbosity.HIGHEST);

			conditionList.add(new HealthCheck(PlayerHealthSetting,
					VictimHealthSetting));
		}

		return conditionList;
	}

}
