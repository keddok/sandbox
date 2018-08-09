package nl.flotsam.xeger;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.List;

/**
 * User: RMakhmutov
 * Date: 18.01.13
 * Time: 11:26
 */
public class OrderedXeger
{

	private final Automaton automaton;
	private final DirectionEnum direction;
	private int index;
	private boolean switched;

	public OrderedXeger(String regex, DirectionEnum direction)
	{
		assert regex != null;
		this.automaton = new RegExp(regex).toAutomaton();
		this.direction = direction;
	}

	public String generate(String currentValue)
	{
		index = 0;
		switched = false;
		StringBuilder builder = new StringBuilder();

		if (currentValue != null && direction == DirectionEnum.DIRECT)
			currentValue = new StringBuilder(currentValue).reverse().toString();

		generate(builder, automaton.getInitialState(), currentValue);

		if (direction == DirectionEnum.DIRECT)
			builder.reverse();

		return builder.toString();
	}

	private void generate(StringBuilder builder, State state, String currentValue)
	{
		List<Transition> transitions = state.getSortedTransitions(true);
		if (transitions.size() == 0)
		{
			assert state.isAccept();
			return;
		}
		int nroptions = state.isAccept() ? transitions.size() : transitions.size() - 1;
		int option = nroptions;//XegerUtils.getRandomInt(0, nroptions, random);
		if (state.isAccept() && option == 0)
		{          // 0 is considered stop
			return;
		}
		// Moving on to next transition
		Transition transition = transitions.get(option - (state.isAccept() ? 1 : 0));
		appendChoice(builder, transition, currentValue);
		generate(builder, transition.getDest(), currentValue);
	}

	private void appendChoice(StringBuilder builder, Transition transition, String currentValue)
	{
		if (currentValue == null)
		{
			builder.append(transition.getMin());
			return;
		}

		char currentChar = currentValue.charAt(index);
		if (!switched)
			currentChar = (char)XegerUtils.getNextInt(transition.getMin(), transition.getMax(), currentChar, 1);

		builder.append(currentChar);

		index++;
		if (currentChar != transition.getMin())
			switched = true;
	}

	public enum DirectionEnum
	{
		DIRECT, BACKWARD;
	};
}
