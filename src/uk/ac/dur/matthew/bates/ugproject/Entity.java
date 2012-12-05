package uk.ac.dur.matthew.bates.ugproject;

import java.util.List;

public class Entity extends StaticObject
{
	private boolean active = true;
	private boolean staticPhysics = true;
	private boolean isAffectedByDecal = false;
	private String callbackFunc;
	private List<Entity> connectedProps;
	private String connectionStateChangedCallback;
	private boolean fullGameSave = false;
	private String playerLookAtCallback;
	private boolean playerLookAtCallbackAutoRemove = false;
	private String playerInteractCallback;
	private boolean playerInteractCallbackAutoRemove = false;

	protected String userVariablesDelegate()
	{
		String s = "";
		s += "                    <Var Name=\"CastShadows\" Value=\"" + doesCastShadows() + "\" />\n";
		s += "                    <Var Name=\"StaticPhysics\" Value=\"" + staticPhysics + "\" />\n";
		s += "                    <Var Name=\"IsAffectedByDecal\" Value=\"" + isAffectedByDecal + "\" />\n";
		s += "                    <Var Name=\"CallbackFunc\" Value=\"" + (callbackFunc != null ? callbackFunc : "") + "\" />\n";
		// TODO implement this properly by enumerating the list once a format is decided upon
		s += "                    <Var Name=\"ConnectedProps\" Value=\"" + "" + "\" />\n";
		s += "                    <Var Name=\"ConnectionStateChangedCallback\" Value=\"" + (connectionStateChangedCallback != null ? connectionStateChangedCallback
				: "") + "\" />\n";
		s += "                    <Var Name=\"FullGameSave\" Value=\"" + fullGameSave + "\" />\n";
		s += "                    <Var Name=\"PlayerLookAtCallback\" Value=\"" + (playerLookAtCallback != null ? playerLookAtCallback : "") + "\" />\n";
		s += "                    <Var Name=\"PlayerLookAtCallbackAutoRemove\" Value=\"" + playerLookAtCallbackAutoRemove + "\" />\n";
		s += "                    <Var Name=\"PlayerInteractCallback\" Value=\"" + (playerInteractCallback != null ? playerInteractCallback : "") + "\" />\n";
		s += "                    <Var Name=\"PlayerInteractCallbackAutoRemove\" Value=\"" + playerInteractCallbackAutoRemove + "\" />\n";
		return s;
	}

	public String toString()
	{
		String s = "<Entity ";
		s += "Active=\"" + active + "\" ";
		s += "FileIndex=\"" + getFileIndex() + "\" ";
		s += "Group=\"" + getGroup() + "\" ";
		s += "ID=\"" + getId() + "\" ";
		s += "Name=\"" + (getName() != null ? getName() : "") + "\" ";
		s += "Rotation=\"" + Math.toRadians(getRotation()[0]) + " " + Math
				.toRadians(getRotation()[1]) + " " + Math.toRadians(getRotation()[2]) + "\" ";
		s += "Scale=\"" + getScale()[0] + " " + getScale()[1] + " " + getScale()[2] + "\" ";
		s += "Tag=\"" + (getTag() != null ? getTag() : "") + "\" ";
		s += "WorldPos=\"" + getWorldPos()[0] + " " + getWorldPos()[1] + " " + getWorldPos()[2] + "\">\n";
		s += "                <UserVariables>\n";
		s += userVariablesDelegate();
		s += "                </UserVariables>\n";
		s += "            </Entity>";
		return s;
	}

}
