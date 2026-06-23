import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

	private final Map<String, VariableInfo> variables = new HashMap<>();

	public boolean exists(String name) {
		return variables.containsKey(name);
	}

	public void declare(String name, String type, Object value) {
		variables.put(name, new VariableInfo(type, value));
	}

	public VariableInfo get(String name) {
		return variables.get(name);
	}

	public void assign(String name, Object value) {
		variables.get(name).setValue(value);
	}
}