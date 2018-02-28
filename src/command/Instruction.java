package command;

import java.io.Serializable;

public interface Instruction extends Serializable {
	public void run();
}
