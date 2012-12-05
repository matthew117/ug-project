package uk.ac.dur.matthew.bates.ugproject;

public class Lamp extends Entity
{
	private boolean lit = true;
	private String connectedLight;
	private float connectionLightAmount = 1.0f;
	private boolean connectionLightUseOnColor = true;
	private boolean connectionLightUseSpec = false;
	
	protected String userVariablesDelegate()
	{
		String s = super.userVariablesDelegate();
		s += "                   <Var Name=\"Lit\" Value=\"" + lit + "\" />\n";
		s += "                   <Var Name=\"ConnectedLight\" Value=\"" + (connectedLight != null ? connectedLight : "") + "\" />\n";
		s += "                   <Var Name=\"ConnectionLightAmount\" Value=\"" + connectionLightAmount + "\" />\n";
		s += "                   <Var Name=\"ConnectionLightUserOnColor\" Value=\"" + connectionLightUseOnColor + "\" />\n";
		s += "                   <Var Name=\"ConnectionLightUseSpac\" Value=\"" + connectionLightUseSpec + "\" />\n";
		return s;
	}
	
}
