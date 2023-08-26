package com.halestudios.data.utility;

import co.aikar.commands.BaseCommand;
import com.halestudios.data.data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static com.halestudios.data.utility.LocationUtils.stringToLocation;

public class WorldUtils extends BaseCommand {

    @Getter
    public static World world = Bukkit.getWorlds().get(0);

    public WorldUtils() {
        world = Bukkit.getWorlds().get(0);
    }


    public static void desapairTemporalyBlocks(String location, int seconds) {

        Block block = stringToLocation(location).getWorld().getBlockAt(stringToLocation(location));
        Material defaultMaterial = block.getType();
        new BukkitRunnable() {
            boolean isVisible = true;

            @Override
            public void run() {
                if (isVisible) {
                    block.setType(Material.AIR);
                } else {
                    block.setType(defaultMaterial);
                }
                isVisible = !isVisible;
            }
        }.runTaskTimer(data.getInstance(), 0, seconds * 20L);
    }

    public static void desapairMultiplyTemporalyBlocks(String pos1, String pos2, int seconds) {
        Block corner1 = stringToLocation(pos1).getBlock();
        Block corner2 = stringToLocation(pos2).getBlock();

        int minX = Math.min(corner1.getX(), corner2.getX());
        int minY = Math.min(corner1.getY(), corner2.getY());
        int minZ = Math.min(corner1.getZ(), corner2.getZ());
        int maxX = Math.max(corner1.getX(), corner2.getX());
        int maxY = Math.max(corner1.getY(), corner2.getY());
        int maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = corner1.getWorld().getBlockAt(x, y, z);
                    Material defaultMaterial = block.getType();

                    new BukkitRunnable() {
                        boolean isVisible = true;

                        @Override
                        public void run() {
                            if (isVisible) {
                                block.setType(Material.AIR);
                            } else {
                                block.setType(defaultMaterial);
                            }
                            isVisible = !isVisible;
                        }
                    }.runTaskTimer(data.getInstance(), 0, seconds * 20L);
                }
            }
        }
    }


    public static void reseteableMovingBlocks(String LocationOne, String LocationTwo, BlockFace direction, int distance, int seconds) {
        String[] partsOne = LocationOne.split(" ");
        String[] partsTwo = LocationTwo.split(" ");

        if (partsOne.length != 6 || partsTwo.length != 6) {
            throw new IllegalArgumentException("Invalid format for location strings. Should be 'x1 y1 z1 x2 y2 z2'");
        }

        String s1 = partsOne[0] + " " + partsOne[1] + " " + partsOne[2];
        String s2 = partsOne[3] + " " + partsOne[4] + " " + partsOne[5];
        String n1 = partsTwo[0] + " " + partsTwo[1] + " " + partsTwo[2];
        String n2 = partsTwo[3] + " " + partsTwo[4] + " " + partsTwo[5];

        if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH) {
            reseteableMovingSouthNorth(s1, s2, n1, n2, distance, seconds * 20L);
        }
        if (direction == BlockFace.WEST || direction == BlockFace.EAST) {
            reseteableMovingEastWest(s1, s2, n1, n2, distance, seconds * 20L);
        }
        if (direction == BlockFace.UP || direction == BlockFace.DOWN) {
            reseteableMovingUpDown(s1, s2, n1, n2, distance, seconds * 20L);
        }
    }


    public static void reseteableMovingSouthNorth(String Spos1, String Spos2, String N1, String N2, int distance, long delay) {
        Location southPos1 = stringToLocation(Spos1);
        Location southPos2 = stringToLocation(Spos2);
        Location northPos1 = stringToLocation(N1);
        Location northPos2 = stringToLocation(N2);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                moveBlocksSouth(southPos1, southPos2, distance);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        moveBlocksNorth(northPos2, northPos1, distance);
                    }
                }.runTaskLater(data.getInstance(), delay);
            }
        }.runTaskTimer(data.getInstance(), 0L, 20L * 10);
    }

    public static void reseteableMovingUpDown(String Spos1, String Spos2, String N1, String N2, int distance, long delay) {
        Location southPos1 = stringToLocation(Spos1);
        Location southPos2 = stringToLocation(Spos2);
        Location northPos1 = stringToLocation(N1);
        Location northPos2 = stringToLocation(N2);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                moveBlocksUP(southPos1, southPos2, distance);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        moveBlocksDOWN(northPos2, northPos1, distance);
                    }
                }.runTaskLater(data.getInstance(), delay);
            }
        }.runTaskTimer(data.getInstance(), 0L, 20L * 10);
    }


    public static void reseteableMovingEastWest(String Spos1, String Spos2, String N1, String N2, int distance, long delay) {
        Location southPos1 = stringToLocation(Spos1);
        Location southPos2 = stringToLocation(Spos2);
        Location northPos1 = stringToLocation(N1);
        Location northPos2 = stringToLocation(N2);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                moveBlocksWest(southPos1, southPos2, distance);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        moveBlocksEast(northPos2, northPos1, distance);
                    }
                }.runTaskLater(data.getInstance(), delay);
            }
        }.runTaskTimer(data.getInstance(), 0L, 20L * 10);
    }


    public static void moveBlocksWest(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.WEST;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = minX[0]; x <= maxX[0]; x++) {
                        for (int y = minY[0]; y <= maxY[0]; y++) {
                            for (int z = minZ[0]; z <= maxZ[0]; z++) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }

    public static void moveBlocksEast(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.EAST;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = maxX[0]; x >= minX[0]; x--) {
                        for (int y = maxY[0]; y >= minY[0]; y--) {
                            for (int z = maxZ[0]; z >= minZ[0]; z--) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }

    public static void moveBlocksUP(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.UP;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = maxX[0]; x >= minX[0]; x--) {
                        for (int y = maxY[0]; y >= minY[0]; y--) {
                            for (int z = maxZ[0]; z >= minZ[0]; z--) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }

    public static void moveBlocksDOWN(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.DOWN;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = minX[0]; x <= maxX[0]; x++) {
                        for (int y = minY[0]; y <= maxY[0]; y++) {
                            for (int z = minZ[0]; z <= maxZ[0]; z++) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }

    public static void moveBlocksSouth(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.SOUTH;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = maxX[0]; x >= minX[0]; x--) {
                        for (int y = maxY[0]; y >= minY[0]; y--) {
                            for (int z = maxZ[0]; z >= minZ[0]; z--) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }

    public static void moveBlocksNorth(Location pos1, Location pos2, int distance) {
        BlockFace direction = BlockFace.NORTH;
        int[] minX = {Math.min(pos1.getBlockX(), pos2.getBlockX())};
        int[] minY = {Math.min(pos1.getBlockY(), pos2.getBlockY())};
        int[] minZ = {Math.min(pos1.getBlockZ(), pos2.getBlockZ())};
        int[] maxX = {Math.max(pos1.getBlockX(), pos2.getBlockX())};
        int[] maxY = {Math.max(pos1.getBlockY(), pos2.getBlockY())};
        int[] maxZ = {Math.max(pos1.getBlockZ(), pos2.getBlockZ())};

        int dx = direction.getModX();
        int dy = direction.getModY();
        int dz = direction.getModZ();

        new BukkitRunnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < distance) {
                    for (int x = minX[0]; x <= maxX[0]; x++) {
                        for (int y = minY[0]; y <= maxY[0]; y++) {
                            for (int z = minZ[0]; z <= maxZ[0]; z++) {
                                Block sourceBlock = pos1.getWorld().getBlockAt(x, y, z);
                                Block targetBlock = sourceBlock.getRelative(dx, dy, dz);

                                if (targetBlock.getType() == Material.AIR) {
                                    targetBlock.setType(sourceBlock.getType());
                                    sourceBlock.setType(Material.AIR);
                                }
                            }
                        }
                    }

                    minX[0] += dx;
                    minY[0] += dy;
                    minZ[0] += dz;
                    maxX[0] += dx;
                    maxY[0] += dy;
                    maxZ[0] += dz;

                    currentStep++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(data.getInstance(), 0, 2);
    }
}

