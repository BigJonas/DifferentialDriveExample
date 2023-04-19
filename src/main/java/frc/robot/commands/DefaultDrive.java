// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {
  private final Drivetrain mDrive;

  private final DoubleSupplier mForwardSupplier;
  private final DoubleSupplier mRotationSupplier;

  /** Creates a new DefaultDrive. */
  public DefaultDrive(Drivetrain drive, DoubleSupplier fwd, DoubleSupplier rot) {
    mDrive = drive;
    mForwardSupplier = fwd;
    mRotationSupplier = rot;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double fwd = mForwardSupplier.getAsDouble();
    double rot = mRotationSupplier.getAsDouble();

    drive(fwd, rot);
  }

  // Protected so children can access this too
  protected void drive(double fwd, double rot) {
    mDrive.drive(fwd, rot);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
