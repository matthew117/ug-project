package uk.ac.dur.matthew.bates.ugproject.hpl2;

public class Door extends Entity
{
	private boolean locked = false;
	private float openAmount = 0.0f;
	private boolean disableBreakable = false;
	
	protected String userVariablesDelegate()
	{
		String s = super.userVariablesDelegate();
		s += "<Var Name=\"Locked\" Value=\"" + locked + "\" />\n";
		s += "<Var Name=\"OpenAmount\" Value=\"" + openAmount + "\" />\n";
		s += "<Var Name=\"DisableBreakable\" Value=\"" + disableBreakable + "\" />\n";
		return s;
	}
}
