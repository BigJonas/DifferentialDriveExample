// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class DifferentialDrive {
    // Square drive train with the corner wheels being 19 inches apart from each other
    // idk this is an example im not a desinger
    public static final double TRACK_WIDTH = Units.inchesToMeters(19); // Meters
    public static final double WHEEL_BASE = Units.inchesToMeters(19); // Meters 

    public static final double WHEEL_CIRCUMFERENCE = Units.inchesToMeters(4.0); // Meters
    public static final double GEAR_RATIO = 10.0; 

    public static final double MAX_WHEEL_SPEED = Neo.FREE_SPEED / 60.0 / GEAR_RATIO / WHEEL_CIRCUMFERENCE; // Meters per second
    public static final double MAX_ANGULAR_VELOCITY = MAX_WHEEL_SPEED / Math.hypot(TRACK_WIDTH / 2.0, WHEEL_BASE / 2.0); // Radians per second

    // TODO: not quite sure if you need to divide by cpr
    public static final double ROTATION_PER_METER = WHEEL_CIRCUMFERENCE / GEAR_RATIO / Neo.COUNTS_PER_REVOLUTION; 

    public static final int PID_SLOT = 0;

    public static final double WHEEL_KP = 0.0;
    public static final double WHEEL_KI = 0.0;
    public static final double WHEEL_KD = 0.0;
    public static final double WHEEL_KF = 0.0; // Voltage to overcome static friction;
  }

  public static class Neo {
    public static final double COUNTS_PER_REVOLUTION = 48.0;
    public static final double FREE_SPEED = 5820; 
  }
}
