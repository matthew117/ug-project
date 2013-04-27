package uk.ac.dur.matthew.bates.ugproject.generators;

import static uk.ac.dur.matthew.bates.ugproject.data.RoomConnectionStats.connectsTo;
import static uk.ac.dur.matthew.bates.ugproject.data.RoomConnectionStats.connectsToPublicOnly;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.append;
import static uk.ac.dur.matthew.bates.ugproject.util.ListUtils.random;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.model.FloorPlan;
import uk.ac.dur.matthew.bates.ugproject.model.Rect;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;
import uk.ac.dur.matthew.bates.ugproject.model.RoomAllocationRule;

public class BFSRoomAllocation implements RoomAllocationRule
{

	@Override
	public List<RoomType> allocateRoomTypes(int frontRoomID, FloorPlan fp)
	{
		List<Integer> bfs = fp.breadthFirstTransversal(frontRoomID);
		List<RoomType> xs = new ArrayList<RoomType>();
		xs.add(RoomType.FOYER);
		for (int i = 1; i < bfs.size(); i++)
		{
			boolean foundType = false;

			for (int j = 1; i - j >= 0; j++)
			{
				Rect r = fp.roomBounds().get(i);
				float aspectRatio = 0.0f;

				if (r.width > r.height)
				{
					aspectRatio = r.width / (float) r.height;
				}
				else
				{
					aspectRatio = r.height / (float) r.width;
				}

				if (aspectRatio > 2 && fp.getAdjacents(i).size() >= 4)
				{
					xs.add(RoomType.CORRIDOR);
					foundType = true;
					break;
				}

				List<RoomType> potentials;
				if (i < 4)
				{
					RoomType a = xs.get(i - j);
					potentials = connectsToPublicOnly(a, append(xs, RoomType.CORRIDOR));
				}
				else
				{
					RoomType b = xs.get(i - j);
					potentials = connectsTo(b, append(xs, RoomType.CORRIDOR));
				}
				RoomType t = random(potentials);
				if (t != null)
				{
					xs.add(t);
					foundType = true;
					break;
				}
			}

			if (!foundType)
			{
				System.out.println("No Choice! Adding a corridor.");
				xs.add(RoomType.CORRIDOR);
			}

		}
		return xs;
	}

}
