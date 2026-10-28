/* Copyright (c) 2017 FIRST. All rights reserved.
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

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * This OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 */

@TeleOp(name="Robot: CYBER HORSE", group="Robot")
public class CyberHorse extends LinearOpMode {

    /* Declare OpMode members. */
    MyRobot robot = new MyRobot(this);

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addLine();
        telemetry.addData("No mercy for the living, no rest for the dead!", "Deus Vult! Ready. Press Play.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Slow Mode
            // We chose 0.3 because it's the lowest power level that consistently
            // overcomes the static friction of the 40A Durometer rollers.
            final double PRECISION_SCALER = 0.3;  // "Slow Mode" (30% power) - Applies to Strafe, Drive, and Spin
            final double FULL_POWER = 1.0;        // Standard Mode (100% power)

            // --- Gamepad 1: Driving Controls ---
            // --- 2. DRIVETRAIN CALCULATION (MECANUM) ---// Inputs from Gamepad 1 (Liam - Drivetrain Pilot)
            double y  = -gamepad1.left_stick_y; // Forward/Backward
            double x  =  gamepad1.left_stick_x; // Strafing
            double rx =  gamepad1.right_stick_x; // Rotation (Spin)

            // --- 3. THE SLOW MODE LOGIC ---
            // If Left Bumper is held, apply the 0.3 scaler.
            // This is critical for the "Mesure" callout to ensure alignment is perfect.
            // Note: This scales the entire vector sum, meaning rotation is also slowed.
            double currentScaler = gamepad1.left_bumper ? PRECISION_SCALER : FULL_POWER;

            // Vector Mapping Formula (Aligned with Drivetrain Evolution Math)
            // Math: Power = (Directional Vectors + Rotation Vector) * Scaler
            double fl = ((-y + x) + rx) * currentScaler;
            double bl = ((-y - x) + rx) * currentScaler;
            double fr = ((-y - x) - rx) * currentScaler;
            double br = ((-y + x) - rx) * currentScaler;

            // Apply to Motors using the correct hardware handles
            robot.frontLeftDrive.setPower(fl);
            robot.backLeftDrive.setPower(bl);
            robot.frontRightDrive.setPower(fr);
            robot.backRightDrive.setPower(br);

            // --- 4. TELEMETRY FEEDBACK ---
            // This helps (Drive Coach) confirm the robot is in the correct mode
            if (currentScaler == PRECISION_SCALER) {
                telemetry.addLine();
                telemetry.addData("DRIVE STATUS", "SLOW MODE ACTIVE (30%)");
                telemetry.addData("ONWARDS", "STEAL OVER BONE!");
                telemetry.update();
            }
            else {
                telemetry.addData("DRIVE STATUS", "FULL SPEED (100%)");
            }
                telemetry.update();

            // --- Gamepad 2: Secondary Controls ---

            //This will control the launcher AKA Pollinator

            if (gamepad2.left_trigger_pressed) {
                robot.Pollinator.setPower(1);
                telemetry.addLine();
                telemetry.addData("Pollinator Status", "Currently Active, Positive Direction");
                telemetry.update();
            }
            else if (gamepad2.right_trigger_pressed) {
                robot.Pollinator.setPower(-1);
                telemetry.addLine();
                telemetry.addData("Pollinator Status", "Currently Active, Negative Direction");
                telemetry.update();
            }
            else if (gamepad2.left_bumper) {
                robot.Pollinator.setPower(0);
                telemetry.addLine();
                telemetry.addData("Pollinator Status", "InActive, Press Left Trigger to activate Pollinator(Launcher)");
                telemetry.update();
            }

            /* Step 2, write code for any other none driving controls and or, functional here.
            Remember the goal for this season, to try and mitigate the possibility of human error while in my opmode
            So functions will be my best friend.
            */
//!
            //Write any other Teleop controls Over here

            // This is the game element transfer system (wind)
            if (gamepad2.dpad_left) {
                robot.Transfer.setPower(.25);
                telemetry.addLine();
                telemetry.addData("Transfer System Status","Active Positive");
                telemetry.update();
            }
            else if (gamepad2.dpad_right) {
                robot.Transfer.setPower(-.25);
                telemetry.addLine();
                telemetry.addData("Transfer System Status","Negative Positive");
                telemetry.update();
            }
            else {

                telemetry.addData("Transfer System Status","InActive");
                telemetry.update();
            }

        }
        if (gamepad2.dpad_up) {
            robot.flowerRamp.setPosition(1);
            telemetry.addLine();
            telemetry.addData("FlowerRamp Status", "Active Positive");
            telemetry.update();
        }
        else if (gamepad2.dpad_down) {
            robot.flowerRamp.setPosition(-1);
            telemetry.addLine();
            telemetry.addData("FlowerRamp Status", "Active negative");
            telemetry.update();
        }
        else
            telemetry.addLine();
            telemetry.addData("FlowerRamp Status", "Inactive");
            telemetry.update();
        }
    }