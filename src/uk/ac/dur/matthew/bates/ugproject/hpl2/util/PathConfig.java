package uk.ac.dur.matthew.bates.ugproject.hpl2.util;

import java.util.ArrayList;
import java.util.List;

import uk.ac.dur.matthew.bates.ugproject.hpl2.Entity;
import uk.ac.dur.matthew.bates.ugproject.model.Room.RoomType;

public class PathConfig
{
	// @formatter:off
	
	public static final String AMNESIA_RESOURCES_DIR = "/Applications/Amnesia.app/Contents/Resources/";

	// Bathroom
	public static final String BATHTUB = AMNESIA_RESOURCES_DIR + "entities/white_night/bathtub/bathtub_no_plump.ent";
	public static final String HANDWASH_BASIN = AMNESIA_RESOURCES_DIR + "entities/white_night/handwash/furniture_handwash_basin.ent";
	public static final String TOILET = AMNESIA_RESOURCES_DIR + "entities/white_night/toilet/toilet_chair.ent";
	public static final String TOILET_PAPER = AMNESIA_RESOURCES_DIR + "entities/white_night/toilet_toilet_paper/toilet_toilet_paper.ent";
	public static final String SOAP_STONE = AMNESIA_RESOURCES_DIR + "entities/ptest/soapstone_star/soapstone_star.ent";

	// Kitchen
	public static final String COOKER = AMNESIA_RESOURCES_DIR + "entities/white_night/cooker/kitchen_cooker.ent";
	public static final String FRIDGE = AMNESIA_RESOURCES_DIR + "entities/white_night/fridge/kitchen_fridge.ent";
	public static final String FRYING_PAN = AMNESIA_RESOURCES_DIR + "entities/white_night/pan/kitchen_frying_pan.ent";
	public static final String KITCHEN_SINK = AMNESIA_RESOURCES_DIR + "entities/white_night/sink/kitchen_sink.ent";
	public static final String TRASH_CAN = AMNESIA_RESOURCES_DIR + "entities/white_night/trashcan/cleaning_trashcan.ent";
	public static final String BOTTLE_AMNESIA = AMNESIA_RESOURCES_DIR + "entities/bottle/amnesia/amnesia_bottle.ent";
	public static final String WINE_BOTTLE = AMNESIA_RESOURCES_DIR + "entities/bottle/wine02/wine02.ent";
	public static final String HEALTH_POTION = AMNESIA_RESOURCES_DIR + "entities/item/potion_health/potion_health.ent";
	public static final String OIL_POTION = AMNESIA_RESOURCES_DIR + "entities/item/potion_oil/potion_oil.ent";
	public static final String SANITY_POTION = AMNESIA_RESOURCES_DIR + "entities/item/potion_sanity/potion_sanity.ent";
	public static final String KNIFE = AMNESIA_RESOURCES_DIR + "entities/kitchen/butcher_knife/butcher_knife.ent";
	public static final String PLATE = AMNESIA_RESOURCES_DIR + "entities/kitchen/plate/plate.ent";
	public static final String SHELVES = AMNESIA_RESOURCES_DIR + "entities/lab/chemestry_shelf/chemestry_shelf_small.ent";
	public static final String HAMMER = AMNESIA_RESOURCES_DIR + "entities/item/stone_hammer/stone_hammer.ent";
	public static final String STOVE_DUNGEON = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_dungeon_front01/stove_dungeon_front01.ent";
	public static final String DINING_TABLE = AMNESIA_RESOURCES_DIR + "entities/furniture/table_nice02/table_nice02.ent";
	public static final String BOTTLE_ABSINTHE = AMNESIA_RESOURCES_DIR + "entities/ptest/bottle_absinthe/bottle_absinthe.ent";
	public static final String BOTTLE_LITHIUM = AMNESIA_RESOURCES_DIR + "entities/ptest/bottle_lithium_bromide/bottle_lithium_bromide.ent";
	public static final String POTATO_SACK = AMNESIA_RESOURCES_DIR + "entities/ptest/potato_sack/potato_sack_open.ent";
	public static final String POTATO = AMNESIA_RESOURCES_DIR + "entities/ptest/potato_sack/potato_small.ent";

	// Study
	public static final String OFFICE_CHAIR = AMNESIA_RESOURCES_DIR + "entities/white_night/furniture_office_chair/furniture_office_chair01.ent";
	public static final String DESK_LAMP = AMNESIA_RESOURCES_DIR + "entities/white_night/lamps_older_desklamp/lamps_older_desklamp.dae";
	public static final String COMPUTER = AMNESIA_RESOURCES_DIR + "entities/white_night/level00_computer_terminal_spec/level00_computer_terminal.ent";
	public static final String TYPEWRITER = AMNESIA_RESOURCES_DIR + "entities/white_night/typewriter/office_typewriter.ent";
	public static final String INK_BOTTLE = AMNESIA_RESOURCES_DIR + "entities/desktop/ink_bottle/ink_bottle.ent";
	public static final String FEATHER_QUILL = AMNESIA_RESOURCES_DIR + "entities/desktop/pen_feather/pen_feather.ent";
	public static final String PAPER_PILE01 = AMNESIA_RESOURCES_DIR + "entities/desktop/paperpile01/paperpile01.ent";
	public static final String WORK_DESK = AMNESIA_RESOURCES_DIR + "entities/furniture/work_desk/work_desk.ent";
	public static final String WORK_DESK_DOOR = AMNESIA_RESOURCES_DIR + "entities/furniture/work_desk/work_desk_door.ent";
	public static final String LETTER = AMNESIA_RESOURCES_DIR + "entities/ptest/note_envelope/note_envelope.ent";

	// Living Room
	public static final String TELEVISION = AMNESIA_RESOURCES_DIR + "entities/white_night/furniture_tv/furniture_tv.ent";
	public static final String ARMCHAIR = AMNESIA_RESOURCES_DIR + "entities/furniture/armchair/armchair.ent";
	public static final String PIANO = AMNESIA_RESOURCES_DIR + "entities/furniture/piano/piano.ent";
	public static final String SOFA = AMNESIA_RESOURCES_DIR + "entities/furniture/sofa/sofa.ent";
	public static final String STOOL_WOODEN = AMNESIA_RESOURCES_DIR + "entities/furniture/stool_wood/stool_wood.ent";
	public static final String STOVE_CORNER = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_corner/stove_corner.ent";
	public static final String STOVE_FRONT01 = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_front01/stove_front01.ent";
	public static final String STOVE_FRONT02 = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_front02/stove_front02.ent";
	public static final String PHONOGRAPH = AMNESIA_RESOURCES_DIR + "entities/ptest/phonograph/phonograph.ent";

	// Storage
	public static final String BARREL = AMNESIA_RESOURCES_DIR + "entities/container/barrel01/barrel01.ent";
	public static final String CRATE = AMNESIA_RESOURCES_DIR + "entities/container/wood_box02/wood_box02.ent";
	public static final String CHEST = AMNESIA_RESOURCES_DIR + "entities/container/wood_box03/wood_box03.ent";
	public static final String SMALL_CHEST = AMNESIA_RESOURCES_DIR + "entities/gameplay/chest_small/chest_small.ent";
	public static final String TREASURE_CHEST = AMNESIA_RESOURCES_DIR + "entities/gameplay/treasure_chest/treasure_chest.ent";
	public static final String BROOM = AMNESIA_RESOURCES_DIR + "entities/equipment/broom/broom.ent";
	public static final String SHOVEL = AMNESIA_RESOURCES_DIR + "entities/tool/shovel/shovel.ent";
	public static final String BUCKET = AMNESIA_RESOURCES_DIR + "entities/item/wooden_bucket/wooden_bucket.ent";
	public static final String BADMINTOM_RACKET = AMNESIA_RESOURCES_DIR + "entities/ptest/badminton_racket/badminton_racket.ent";
	
	// Bedroom
	public static final String BED_NICE = AMNESIA_RESOURCES_DIR + "entities/furniture/bed_nice/bed_nice.ent";
	public static final String BED_SIMPLE = AMNESIA_RESOURCES_DIR + "entities/furniture/bed_simple/bed_simple.ent";
	public static final String CABINET_METAL = AMNESIA_RESOURCES_DIR + "entities/furniture/cabinet_metal/cabinet_metal.ent";
	public static final String CABINET_NICE = AMNESIA_RESOURCES_DIR + "entities/furniture/cabinet_nice/cabinet_nice.ent";
	public static final String CABINET_SIMPLE = AMNESIA_RESOURCES_DIR + "entities/furniture/cabinet_simple/cabinet_simple.ent";
	public static final String CHAIR_METAL = AMNESIA_RESOURCES_DIR + "entities/furniture/chair_metal/chair_metal.ent";
	public static final String CHAIR_NICE01 = AMNESIA_RESOURCES_DIR + "entities/furniture/chair_nice01/chair_nice01.ent";
	public static final String CHAIR_NICE02 = AMNESIA_RESOURCES_DIR + "entities/furniture/chair_nice02/chair_nice02.ent";
	public static final String CHAIR_WOOD = AMNESIA_RESOURCES_DIR + "entities/furniture/chair_wood/chair_wood.ent";
	public static final String CHAIR_WOOD02 = AMNESIA_RESOURCES_DIR + "entities/furniture/chair_wood02/chair_wood02.ent";
	public static final String CHEST_OF_DRAWERS_NICE = AMNESIA_RESOURCES_DIR + "entities/furniture/chest_of_drawers_nice/chest_of_drawers_nice.ent";
	public static final String CHEST_OF_DRAWER_SIMPLE = AMNESIA_RESOURCES_DIR + "entities/furniture/chest_of_drawers_simple/chest_of_drawers_simple.ent";
	public static final String STOVE_SMALL_METAL = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_metal/stove_metal.ent";
	public static final String STOVE_SMALE_METAL_RUST = AMNESIA_RESOURCES_DIR + "entities/furniture/stove_metal/stove_metal_rust.ent";
	
	// Doors
	public static final String DOOR_MANSION = AMNESIA_RESOURCES_DIR + "entities/door/mansion/mansion.ent";
	
	// Lighting
	public static final String HANGING_LANTERN = AMNESIA_RESOURCES_DIR + "entities/lamp/hanging_lantern/hanging_lantern_ceiling.ent";
	public static final String SIMPLE_CHANDELIER = AMNESIA_RESOURCES_DIR + "entities/lamp/chandelier_simple/chandelier_simple_short.ent";
	public static final String LARGE_CHANDELIER = AMNESIA_RESOURCES_DIR + "entities/lamp/chandelier_large/chandelier_large.ent";
	public static final String CANDLESTICK_FLOOR = AMNESIA_RESOURCES_DIR + "entities/lamp/candlestick_floor/candlestick_floor.ent";
	public static final String CANDLESTICK_TRI = AMNESIA_RESOURCES_DIR + "entities/lamp/candlestick_tri/candlestick_tri.ent";
	public static final String CANDLESTICK_WALL = AMNESIA_RESOURCES_DIR + "entities/lamp/candlestick_wall/candlestick_wall.ent";
	public static final String CANDLESTICK01 = AMNESIA_RESOURCES_DIR + "entities/lamp/candlestick01/candlestick01.ent";
	public static final String CANDLESTICK02 = AMNESIA_RESOURCES_DIR + "entities/lamp/candlestick02/candlestick02.ent";

	// Enemy
	public static final String BRUTE = AMNESIA_RESOURCES_DIR + "entities/enemy/servant_brute/servant_brute.ent";
	
	// Miscellaneous
	public static final String RADIATOR = AMNESIA_RESOURCES_DIR + "entities/white_night/furniture_radiator/furniture_radiator.ent";
	public static final String VASE = AMNESIA_RESOURCES_DIR + "entities/container/vase01/vase01.ent";
	public static final String BELLOWS = AMNESIA_RESOURCES_DIR + "entities/equipment/bellows/bellows.ent";
	public static final String ARMOUR = AMNESIA_RESOURCES_DIR + "entities/statues/armour_nice/armour_nice_complete.ent";
	public static final String TABLE_NICE_ROUND = AMNESIA_RESOURCES_DIR + "entities/furniture/table_nice_round/table_nice_round.ent";
	public static final String TABLE_NICE_WOOD = AMNESIA_RESOURCES_DIR + "entities/furniture/table_nice_wood01/table_nice_wood01.ent";
	public static final String TABLE_WOOD01 = AMNESIA_RESOURCES_DIR + "entities/furniture/table_wood01/table_wood01.ent";
	public static final String TABLE_WOOD02 = AMNESIA_RESOURCES_DIR + "entities/furniture/table_wood02/table_wood02.ent";
	public static final String TABLE_WOOD_SMALL = AMNESIA_RESOURCES_DIR + "entities/furniture/table_wood_small/table_wood_small.ent";
	public static final String BELLOWS02 = AMNESIA_RESOURCES_DIR + "entities/ptest/bellows02/bellows.ent";
	public static final String HATCH = AMNESIA_RESOURCES_DIR + "entities/ptest/hatch_ceiling02/hatch_ceiling02.ent";
	public static final String PAINTING01 = AMNESIA_RESOURCES_DIR + "entities/ptest/paintings_strange/paintings_strange01.ent";
	public static final String PAINTING02 = AMNESIA_RESOURCES_DIR + "entities/ptest/paintings_strange/paintings_strange02.ent";
	public static final String PAINTING03 = AMNESIA_RESOURCES_DIR + "entities/ptest/paintings_strange/paintings_strange03.ent";
	public static final String PAINTING04 = AMNESIA_RESOURCES_DIR + "entities/ptest/paintings_strange/paintings_strange04.ent";
	public static final String VIOLIN = AMNESIA_RESOURCES_DIR + "entities/ptest/violin/violin.ent";
	public static final String VIOLIN_BOW = AMNESIA_RESOURCES_DIR + "entities/ptest/violin/violin_bow.ent";
	public static final String MATRESS = AMNESIA_RESOURCES_DIR + "entities/ptest/worn_hay_mattress_slow/worn_hay_mattress_slow.ent";
	public static final String SUIT_OF_ARMOR = AMNESIA_RESOURCES_DIR + "entities/ornament/armor/armor.ent";
	public static final String BANNER01 = AMNESIA_RESOURCES_DIR + "entities/ornament/banner01/banner01.ent";
	public static final String BANNER02 = AMNESIA_RESOURCES_DIR + "entities/ornament/banner02/banner02.ent";
	public static final String BOOK01 = AMNESIA_RESOURCES_DIR + "entities/ornament/book01/book01.ent";
	public static final String BOOK02 = AMNESIA_RESOURCES_DIR + "entities/ornament/book02/book02.ent";
	public static final String BOOK03 = AMNESIA_RESOURCES_DIR + "entities/ornament/book03/book03.ent";
	public static final String BOOK_BIBLE = AMNESIA_RESOURCES_DIR + "entities/ornament/book_bible/book_bible.ent";
	public static final String BOOK_OPEN = AMNESIA_RESOURCES_DIR + "entities/ornament/book_open/book_open.ent";
	public static final String BOOK_PILE01 = AMNESIA_RESOURCES_DIR + "entities/ornament/book_pile01/book_pile01.ent";
	public static final String BOOK_PILE02 = AMNESIA_RESOURCES_DIR + "entities/ornament/book_pile02/book_pile02.ent";
	public static final String BOOK_ROW01 = AMNESIA_RESOURCES_DIR + "entities/ornament/book_row01/book_row01.ent";
	public static final String BOOK_ROW02 = AMNESIA_RESOURCES_DIR + "entities/ornament/book_row02/book_row02.ent";
	public static final String BOOK_ROW03 = AMNESIA_RESOURCES_DIR + "entities/ornament/book_row03/book_row03.ent";
	public static final String CLOCK_DESK = AMNESIA_RESOURCES_DIR + "entities/ornament/clock_desk/clock_desk.ent";
	public static final String CLOCK_GRANDFATHER = AMNESIA_RESOURCES_DIR + "entities/ornament/clock_grandfather/clock_grandfather.ent";
	public static final String CLOTHES_HOOK = AMNESIA_RESOURCES_DIR + "entities/ornament/clothes_hook/clothes_hook.ent";
	public static final String GOBLET = AMNESIA_RESOURCES_DIR + "entities/ornament/goblet/goblet.ent";
	public static final String ORCHID = AMNESIA_RESOURCES_DIR + "entities/ornament/orchid/orchid.ent";
	public static final String PAINTING_DIEGO_CHURCH = AMNESIA_RESOURCES_DIR + "entities/ornament/painting_diego_church/painting_diego_church.ent";
	public static final String POTTED_PLANT = AMNESIA_RESOURCES_DIR + "entities/ornament/pot_plant_small01/pot_plant_small01.ent";
	public static final String SHIELD01 = AMNESIA_RESOURCES_DIR + "entities/ornament/shield01/shield01.ent";
	public static final String SHIELD02 = AMNESIA_RESOURCES_DIR + "entities/ornament/shield02/shield02.ent";
	public static final String MECHANICAL_SKETCH01 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical01.ent";
	public static final String MECHANICAL_SKETCH02 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical02.ent";
	public static final String MECHANICAL_SKETCH03 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical03.ent";
	public static final String MECHANICAL_SKETCH04 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical04.ent";
	public static final String MECHANICAL_SKETCH05 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical05.ent";
	public static final String MECHANICAL_SKETCH06 = AMNESIA_RESOURCES_DIR + "entities/ornament/sketch_mechanical/sketch_mechanical06.ent";
	public static final String TOME01 = AMNESIA_RESOURCES_DIR + "entities/ornament/tome01/tome01.ent";
	public static final String TOME_OPEN01 = AMNESIA_RESOURCES_DIR + "entities/ornament/tome_open01/tome_open01.ent";
	public static final String TIME_OPEN02 = AMNESIA_RESOURCES_DIR + "entities/ornament/tome_open02/tome_open02.ent";
	public static final String TOME_PILE01 = AMNESIA_RESOURCES_DIR + "entities/ornament/tome_pile01/tome_pile01.ent";
	public static final String TOME_PILE02 = AMNESIA_RESOURCES_DIR + "entities/ornament/tome_pile02/tome_pile02.ent";
	
	// Materials
	public static final String MANSION_FLOOR_MATERIAL = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/floor/mansionbase_floor.mat";
	public static final String MANSION_FLOORBOARDS_MATERIAL = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/floor/mansionbase_floor_boards.mat";
	public static final String TILED_FLOOR_MATERIAL = AMNESIA_RESOURCES_DIR + "/static_objects/castlebase/castlebase_tiledfloor.mat";
	
	// Static_Objects
	
	// Walls
	public static final String MANSION_WALL_WHITE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/default.dae";
	public static final String MANSION_HALFWALL_WHITE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/short.dae";
	public static final String MANSION_WALL_WHITE_SECRET = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/default_secret.dae";
	
	public static final String MANSION_WALL_RED = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/default02.dae";
	public static final String MANSION_HALFWALL_RED = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/short02.dae";
	public static final String MANSION_WALL_RED_SECRET = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/default02_secret.dae";
	
	public static final String MANSION_WALL_EXTENSION = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/default_extension.dae";
	public static final String MANSION_DOOR_FRAME = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/door_frame.dae";
	public static final String MANSION_DOORWAY_WHITE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/door_way.dae";
	public static final String MANSION_DOORWAY_RED = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/door_way02.dae";
	
	public static final String CELLAR_WALL_BROKEN = AMNESIA_RESOURCES_DIR + "static_objects/cellarbase/wall/default03.dae";
	public static final String CELLAR_WALL = AMNESIA_RESOURCES_DIR + "static_objects/cellarbase/wall/default04.dae";
	public static final String CELLAR_SHORT = AMNESIA_RESOURCES_DIR + "static_objects/cellarbase/wall/short.dae";
	public static final String CELLAR_DOORWAY = AMNESIA_RESOURCES_DIR + "static_objects/cellarbase/wall/door_way.dae";
	
	public static final String WALL_STOVE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/wall_stove.dae";
	
	// Windows
	public static final String WINDOW02_BLUE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window02_blue.dae";
	public static final String WINDOW02_LARGE_BLUE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window02_large_blue.dae";
	public static final String WINDOW02_LARGE_YELLOW = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window02_large_yellow.dae";
	public static final String WINDOW02_YELLOW = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window02_yellow.dae";
	public static final String WINDOW03_BLUE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window03_blue.dae";
	public static final String WINDOW03_YELLOW = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window03_yellow.dae";
	public static final String WINDOW_BLUE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window_blue.dae";
	public static final String WINDOW_LARGE_BLUE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window_large_blue.dae";
	public static final String WINDOW_LARGE_BLUE_SEETHROUGH = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window_large_blue_seethrough.dae";
	public static final String WINDOW_LARGE_YELLOW = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window_large_yellow.dae";
	public static final String WINDOW_YELLOW = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/window_yellow.dae";
	
	// Ceilings
	public static final String CEILING_CONCAVE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/ceiling/concave.dae";
	public static final String CEILING_CONCAVE_SHORT = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/ceiling/concave_short.dae";
	public static final String CEILING_CORNER = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/ceiling/corner_concave_short.dae";
	public static final String CIELING_CONVEX = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/ceiling/corner_convex_short.dae";
	
	// Welders
	public static final String PILLAR = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/pillar02.dae";
	public static final String WELDER = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/welder.dae";
	public static final String WLEDER02_CONCAVE = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/welder02_concave.dae";
	public static final String WELDER02_CONVEX = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/wall/welder02_convex.dae";
	
	// Stairs
	public static final String STAIRS = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/stairs/default.dae";
	public static final String RAILING = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/stairs/railing.dae";
	public static final String STAIR_WELDER = AMNESIA_RESOURCES_DIR + "static_objects/mansionbase/stairs/welder.dae";
	
	// @formatter:on
	
	public static List<String> getObjectListByRoomType(RoomType r)
	{
		List<String> xs = new ArrayList<String>();
		
		switch (r)
		{
		case BATHROOM:
			xs.add(BATHTUB);
			xs.add(HANDWASH_BASIN);
			xs.add(TOILET);
			xs.add(TOILET_PAPER);
			break;
		case BEDROOM:
			xs.add(BED_NICE);
			xs.add(CABINET_NICE);
			xs.add(CHEST_OF_DRAWERS_NICE);
			xs.add(STOVE_SMALL_METAL);
			break;
		case CORRIDOR:
			xs.add(PAINTING01);
			xs.add(PAINTING02);
			xs.add(PAINTING03);
			xs.add(PAINTING04);
			break;
		case DINING_ROOM:
			xs.add(DINING_TABLE);
			xs.add(PLATE);
			xs.add(DINING_TABLE);
			xs.add(CHAIR_NICE01);
			xs.add(CHAIR_NICE02);
			xs.add(CHAIR_WOOD);
			xs.add(CHAIR_WOOD02);
			break;
		case FOYER:
			xs.add(RADIATOR);
			break;
		case GUEST_ROOM:
			xs.add(BED_SIMPLE);
			xs.add(CHEST_OF_DRAWER_SIMPLE);
			break;
		case KITCHEN:
			xs.add(KITCHEN_SINK);
			xs.add(FRIDGE);
			xs.add(FRYING_PAN);
			xs.add(SHELVES);
			break;
		case LAUNDRY:
			xs.add(BARREL);
			break;
		case LIVING_ROOM:
			xs.add(SOFA);
			xs.add(CHEST_OF_DRAWERS_NICE);
			xs.add(TELEVISION);
			xs.add(ARMCHAIR);
			xs.add(PIANO);
			xs.add(PHONOGRAPH);
			xs.add(STOOL_WOODEN);
			xs.add(STOVE_FRONT01);
			break;
		case MASTER_BEDROOM:
			xs.add(BED_NICE);
			xs.add(CABINET_NICE);
			xs.add(CHEST_OF_DRAWERS_NICE);
			xs.add(STOVE_SMALL_METAL);
			break;
		case STORAGE:
			xs.add(BARREL);
			xs.add(BUCKET);
			xs.add(CRATE);
			xs.add(CHEST);
			xs.add(TREASURE_CHEST);
			xs.add(BROOM);
			xs.add(SHOVEL);
			xs.add(BUCKET);
			xs.add(BADMINTOM_RACKET);
			break;
		case STUDY:
			xs.add(OFFICE_CHAIR);
			xs.add(DESK_LAMP);
			xs.add(COMPUTER);
			xs.add(TYPEWRITER);
			xs.add(INK_BOTTLE);
			xs.add(FEATHER_QUILL);
			xs.add(PAPER_PILE01);
			xs.add(WORK_DESK);
			xs.add(WORK_DESK_DOOR);
			xs.add(LETTER);
			break;
		case TOILET:
			xs.add(TOILET);
			break;
		default:
			break;
		
		}
		
		return xs;
	}
}
