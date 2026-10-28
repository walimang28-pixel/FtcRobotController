/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or... In OnBot Java, add a new file named RobotHardware.java, select this sample, and select Not an OpMode.
 * Also add a new OpMode, select the sample ConceptExternalHardwareClass.java, and select TeleOp.
 *
 */
//!
public class MyRobot {

    /* Declare OpMode members. */
    public LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    public DcMotor frontLeftDrive   = null;
    public DcMotor frontRightDrive  = null;
    public DcMotor backLeftDrive   = null;
    public DcMotor backRightDrive  = null;
    public DcMotor Pollinator   = null;
    public Servo flowerRamp   = null;
    public CRServo PollerL    = null;
    public CRServo PollerR   = null;
    public CRServo Transfer = null;





    // Define a constructor that allows the OpMode to pass a reference to itself.
    public MyRobot ( LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public MyRobot(RobotTeleopMecanumFieldRelativeDrive robotTeleopMecanumFieldRelativeDrive) {
    }
    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        frontLeftDrive  = myOpMode.hardwareMap.get(DcMotor.class, "fl");
        frontRightDrive = myOpMode.hardwareMap.get(DcMotor.class, "fr");
        backLeftDrive  = myOpMode.hardwareMap.get(DcMotor.class, "bl");
        backRightDrive = myOpMode.hardwareMap.get(DcMotor.class, "br");
        Pollinator = myOpMode.hardwareMap.get(DcMotor.class,"Launcher");
        flowerRamp = myOpMode.hardwareMap.get(Servo.class, "Flower");
        Transfer = myOpMode.hardwareMap.get(CRServo.class, "Transfer");
        PollerL = myOpMode.hardwareMap.get(CRServo.class, "pull left");
        PollerR = myOpMode.hardwareMap.get(CRServo.class, "pull right");



        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
         frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Define and initialize ALL installed servos.
        // {Servos here}


        myOpMode.telemetry.addData(">", "Hardware Initialized");
        myOpMode.telemetry.update();
    }



    public double calculateTickCount(double inches){
        double wheelDiameter = 4.094;
        double circumference =3.14*wheelDiameter;
        return inches/circumference;
    }
    public void stop() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
    }
    public void stopAndResetEncoders(){
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void wait(double seconds, boolean print){
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (myOpMode.opModeIsActive() && (runtime.seconds() < seconds)) {
            myOpMode.telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            myOpMode.telemetry.update();
        }
    }

    public void whatAmIDoing(String message, double seconds) {
        ElapsedTime     runtime = new ElapsedTime();
        runtime.reset();

        myOpMode.telemetry.addData("Path", message);
        myOpMode.telemetry.update();

    }


    public void driveStraight(double power, double seconds) {
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        wait(seconds,true);
        stop();
    }

    public void driveStraightEncoder(double power, double inches) {
        // figure out the tick count (degrees)
        double rotationsNeeded = calculateTickCount(inches);
        int encoderDrivingTarget = (int)(rotationsNeeded*537.6);

        // stop and reset the encoders
        stopAndResetEncoders();
        // set the target position
        frontLeftDrive.setTargetPosition(encoderDrivingTarget);
        frontRightDrive.setTargetPosition(encoderDrivingTarget);
        backLeftDrive.setTargetPosition(encoderDrivingTarget);
        backRightDrive.setTargetPosition(encoderDrivingTarget);
        // set the desired power
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        // set to run to position
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // wait while busy()
        while (
                myOpMode.opModeIsActive() && !(!frontLeftDrive.isBusy() &&
                        !frontRightDrive.isBusy() &&
                        !backLeftDrive.isBusy() &&
                        !backRightDrive.isBusy())) {
            // do nothing
            myOpMode.telemetry.addData("Path", "%4.1f inches", inches);
            myOpMode.telemetry.addData("Path", "%4.1f rotations", rotationsNeeded);
            myOpMode.telemetry.addData("Path", "%d front left", frontLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d front right",frontRightDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back left",  backLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back right", backRightDrive.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        // stop the motor
        stop();
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void driveStrafeEncoder(double power, double inches) {
        // figure out the tick count (degrees)
        double rotationsNeeded = calculateTickCount(inches);
        int encoderDrivingTarget = (int)(rotationsNeeded*1120);
        // stop and reset the encoders
        stopAndResetEncoders();
        // set the target position
        frontLeftDrive.setTargetPosition(encoderDrivingTarget);
        frontRightDrive.setTargetPosition(-encoderDrivingTarget);
        backLeftDrive.setTargetPosition(-encoderDrivingTarget);
        backRightDrive.setTargetPosition(encoderDrivingTarget);
        // set the desired power
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(-power);
        backRightDrive.setPower(power);
        // set to run to position
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // wait while busy()
        while (
                myOpMode.opModeIsActive() && (
                        frontLeftDrive.isBusy() ||
                                frontRightDrive.isBusy() ||
                                backLeftDrive.isBusy() ||
                                backRightDrive.isBusy())) {
            // do nothing
            myOpMode.telemetry.addData("Path", "%4.1f inches", inches);
            myOpMode.telemetry.addData("Path", "%4.1f rotations", rotationsNeeded);
            myOpMode.telemetry.addData("Path", "%d front left", frontLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d front right",frontRightDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back left",  backLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back right", backRightDrive.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        // stop the motor
        stop();

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }






    public void spin(double power, double seconds) {
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(-power);
        wait(seconds,true);
        stop();
    }


    public void driveSpinEncoder(double power, double inches) {
        // figure out the tick count (degrees)
        double rotationsNeeded = calculateTickCount(inches);
        int encoderDrivingTarget = (int)(rotationsNeeded*1120);
        // stop and reset the encoders
        stopAndResetEncoders();
        // set the target position
        frontLeftDrive.setTargetPosition(encoderDrivingTarget);
        frontRightDrive.setTargetPosition(-encoderDrivingTarget);
        backLeftDrive.setTargetPosition(encoderDrivingTarget);
        backRightDrive.setTargetPosition(-encoderDrivingTarget);
        // set the desired power
        frontLeftDrive.setPower(power);
        frontRightDrive.setPower(-power);
        backLeftDrive.setPower(power);
        backRightDrive.setPower(-power);
        // set to run to position
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // wait while busy()
        while (
                myOpMode.opModeIsActive() && (
                        frontLeftDrive.isBusy() ||
                                frontRightDrive.isBusy() ||
                                backLeftDrive.isBusy() ||
                                backRightDrive.isBusy())) {
            // do nothing
            myOpMode.telemetry.addData("Path", "%4.1f inches", inches);
            myOpMode.telemetry.addData("Path", "%4.1f rotations", rotationsNeeded);
            myOpMode.telemetry.addData("Path", "%d front left", frontLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d front right",frontRightDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back left",  backLeftDrive.getCurrentPosition());
            myOpMode.telemetry.addData("Path", "%d back right", backRightDrive.getCurrentPosition());
            myOpMode.telemetry.update();
        }

        // stop the motor
        stop();

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}