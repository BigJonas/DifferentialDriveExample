// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DifferentialDrive.*;

public class Drivetrain extends SubsystemBase {
  private final CANSparkMax mLeftMotor;
  private final RelativeEncoder mLeftEncoder;
  private final SparkMaxPIDController mLeftPID;
  
  private final CANSparkMax mRightMotor;
  private final RelativeEncoder mRightEncoder;
  private final SparkMaxPIDController mRightPID;

  private final DifferentialDriveKinematics mKinematics;

  /** Creates a new Drivetrain. */
  public Drivetrain(CANSparkMax leftMotors[], CANSparkMax rightMotors[], DifferentialDriveKinematics kinematics) {
    mLeftMotor = leftMotors[0];
    mLeftEncoder = mLeftMotor.getEncoder();
    mLeftPID = mLeftMotor.getPIDController();

    mRightMotor = rightMotors[0];
    mRightEncoder = mRightMotor.getEncoder();
    mRightPID = mRightMotor.getPIDController();

    // Sets the motors to follow dont know if going out of scope destroys the object
    // Dont replicate this prolly
    for (int i = 0; i < leftMotors.length; i++) {
      leftMotors[i].follow(mLeftMotor);
    }

    for (int i = 0; i < rightMotors.length; i++) {
      rightMotors[i].follow(mRightMotor);
    }

    mKinematics = kinematics;

    configEncoder();
    configPID();
  }

  /**
   * Drives the drivetrain
   * @param fwd Forward velocity Value range from -1 to 1
   * @param rot Angular velocity Value range from -1 to 1
   */
  public void drive(double fwd, double rot) {
    fwd = MathUtil.clamp(fwd, -1, 1) * MAX_WHEEL_SPEED;
    rot = MathUtil.clamp(rot, -1, 1) * MAX_ANGULAR_VELOCITY;

    ChassisSpeeds chassisSpeeds = new ChassisSpeeds(fwd, 0.0, rot);
    drive(chassisSpeeds); 
  }

  /**
   * Drives the drivetrain
   * @param chassisSpeeds Desired chassis speeds
   */
  public void drive(ChassisSpeeds chassisSpeeds) {
    DifferentialDriveWheelSpeeds wheelSpeeds = mKinematics.toWheelSpeeds(chassisSpeeds);
    drive(wheelSpeeds);
  }

  /**
   * Drives the drivetrain
   * @param wheelSpeeds Desired wheel speeds
   */
  public void drive(DifferentialDriveWheelSpeeds wheelSpeeds) {
    wheelSpeeds.desaturate(MAX_WHEEL_SPEED);
    mLeftPID.setReference(wheelSpeeds.leftMetersPerSecond, ControlType.kVelocity);
  }

  /**
   * Configs the motors encoders
   */
  private void configEncoder() {
    mLeftEncoder.setPositionConversionFactor(ROTATION_PER_METER); // Converts encoder position to meters
    mLeftEncoder.setVelocityConversionFactor(ROTATION_PER_METER / 60.0); // Converts neo rpm to meters per second TODO: double check conversion
    mLeftEncoder.setPosition(0.0);

    mRightEncoder.setPositionConversionFactor(ROTATION_PER_METER); // Converts encoder position to meters
    mRightEncoder.setVelocityConversionFactor(ROTATION_PER_METER / 60.0); // Converts neo rpm to meters per second TODO: double check conversion
    mRightEncoder.setPosition(0.0);

  }

  /**
   * Configs the motors pid controllers
   */
  private void configPID() {
    mLeftPID.setP(WHEEL_KP);
    mLeftPID.setI(WHEEL_KI);
    mLeftPID.setD(WHEEL_KD);
    mLeftPID.setFF(WHEEL_KF);

    mRightPID.setP(WHEEL_KP);
    mRightPID.setI(WHEEL_KI);
    mRightPID.setD(WHEEL_KD);
    mRightPID.setFF(WHEEL_KF);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
