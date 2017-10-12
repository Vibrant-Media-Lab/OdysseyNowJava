//Set up as Teensy++ 2.0 with Serial + Keyboard + Mouse + Joystick

#include <Bounce.h>

//Initialize button pins
const int leftResetPin = PIN_D0;
const int rightResetPin = PIN_D1;
const int speedUpPin = PIN_D2;
const int speedDownPin = PIN_D3;
const int cardAdvancePin = PIN_D4;
const int cardBackPin = PIN_D5;
const int menuPin = PIN_C0;
const int menuTogglePin = PIN_C1;
const int menuSelectPin = PIN_C2;
const int accessoryHitPin = PIN_D6;
const int accessoryResetPin = PIN_D7;

//Initialize push button events
Bounce pushLeftReset = Bounce(leftResetPin, 10);
Bounce pushRightReset = Bounce(rightResetPin, 10);
Bounce pushSpeedUp = Bounce(speedUpPin, 10);
Bounce pushSpeedDown = Bounce(speedDownPin, 10);
Bounce pushCardAdvance = Bounce(cardAdvancePin, 10);
Bounce pushCardBack = Bounce(cardBackPin, 10);
Bounce pushMenu = Bounce(menuPin, 10);
Bounce pushMenuToggle = Bounce(menuTogglePin, 10);
Bounce pushMenuSelect = Bounce(menuSelectPin, 10);
Bounce pushAccessoryHit = Bounce(accessoryHitPin, 10);
Bounce pushAccessoryReset = Bounce(accessoryResetPin, 10);

void setup() {
//set pin mode on pins for buttons
pinMode(leftResetPin, INPUT_PULLUP);
pinMode(rightResetPin, INPUT_PULLUP);
pinMode(speedUpPin, INPUT_PULLUP);
pinMode(speedDownPin, INPUT_PULLUP);
pinMode(cardAdvancePin, INPUT_PULLUP);
pinMode(cardBackPin, INPUT_PULLUP);
pinMode(menuPin, INPUT_PULLUP);
pinMode(menuTogglePin, INPUT_PULLUP);
pinMode(menuSelectPin, INPUT_PULLUP);
pinMode(accessoryHitPin, INPUT_PULLUP);
pinMode(accessoryResetPin, INPUT_PULLUP);
Serial.begin(38400);
}

//Initialize integer representing value for pots
int leftHorizontalVal;
int leftVerticalVal;
int leftEnglishVal;
int rightHorizontalVal;
int rightVerticalVal;
int rightEnglishVal;
int aux1Val;
int aux2Val;
int aux3Val;
int aux4Val;
//3-7 removed because A doesn't accept serial
//int aux3Val;
//int aux4Val;
//int aux5Val;
//int aux6Val;
//int aux7Val;
//int aux8Val;

void loop() {
//Constantly check for pot position(value)
//Only updates if the new value is greater or less than by 3
int newLeftHorizontalVal = analogRead(PIN_F1); //Left Horizontal Pot Value
if(newLeftHorizontalVal-leftHorizontalVal>=3||newLeftHorizontalVal-leftHorizontalVal<=-3){
  //Serial.println(newLeftHorizontalVal);
  leftHorizontalVal = newLeftHorizontalVal;
   
}
int newLeftVerticalVal = analogRead(PIN_F0); //Left Vertical Pot Value
if(newLeftVerticalVal - leftVerticalVal>=3||newLeftVerticalVal - leftVerticalVal<=-3){
  //Serial.println(newLeftVerticalVal);
  leftVerticalVal = newLeftVerticalVal;
   
}
int newLeftEnglishVal = analogRead(PIN_F2); //Left English Pot Value
if(newLeftEnglishVal - leftEnglishVal>=3||newLeftEnglishVal - leftEnglishVal<=-3){
  //Serial.println(newLeftEnglishVal);
  leftEnglishVal = newLeftEnglishVal;
   
}
int newRightHorizontalVal = analogRead(PIN_F4); //Right Horizontal pot Value
if(newRightHorizontalVal - rightHorizontalVal>=3||newRightHorizontalVal - rightHorizontalVal<=-3){
  //Serial.println(newRightHorizontalVal);
  rightHorizontalVal = newRightHorizontalVal;
   
}
int newRightVerticalVal = analogRead(PIN_F3); //Right Vertical Pot Value
if(newRightVerticalVal - rightVerticalVal>=3||newRightVerticalVal - rightVerticalVal<=-3){
  //Serial.println(newRightVerticalVal);
  rightVerticalVal = newRightVerticalVal;
   
}
int newRightEnglishVal = analogRead(PIN_F5); //Right English Pot Value
if(newRightEnglishVal - rightEnglishVal>=3||newRightEnglishVal - rightEnglishVal<=-3){
  //Serial.println(newRightEnglishVal);
  rightEnglishVal = newRightEnglishVal;
   
}
int newAux1Val = analogRead(PIN_F6); //Aux Values, Pots 1-8, changed from A to F
if(newAux1Val - aux1Val>=3||newAux1Val - aux1Val<=-3){
  //Serial.println(newAux1Val);
  aux1Val = newAux1Val;
   
}
int newAux2Val = analogRead(PIN_F7); //Same
if(newAux2Val - aux2Val>=3||newAux2Val - aux2Val<=-3){
  //Serial.println(newAux2Val);
  aux2Val = newAux2Val;
   
}
//aux3Val = analogRead(PIN_D6);
//aux4Val = analogRead(PIN_D7);

//Send values to serial port, 8 at a time on different lines
//Serial.println(leftHorizontalVal);
//Serial.println(leftVerticalVal);
//Serial.println(leftEnglishVal);
//Serial.println(rightHorizontalVal);
//Serial.println(rightVerticalVal);
//Serial.println(rightEnglishVal);
//Serial.println(aux1Val);
//Serial.println(aux2Val);

//Send values on one line, comma delimited
Serial.print(leftHorizontalVal);
Serial.print(",");
Serial.print(leftVerticalVal);
Serial.print(",");
Serial.print(leftEnglishVal);
Serial.print(",");
Serial.print(rightHorizontalVal);
Serial.print(",");
Serial.print(rightVerticalVal);
Serial.print(",");
Serial.print(rightEnglishVal);
Serial.print(",");
Serial.print(aux1Val);
Serial.print(",");
Serial.print(aux2Val);
//Serial.print(",");
//Serial.print(aux3Val);
//Serial.print(",");
//Serial.print(aux4Val);
Serial.println();




//if you only want to send values to serial port when a new value is generated, do this
//newVal = analogRead(pin);
//if(newVal != val){
//  Serial.println(newVal);
//  val = newVal;
//}

//aux 3-8 removed because A doesn't accept serial
//aux3Val = analogRead(PIN_A2);
//aux4Val = analogRead(PIN_A3);
//aux5Val = analogRead(PIN_A4);
//aux6Val = analogRead(PIN_A5);
//aux7Val = analogRead(PIN_A6);
//aux8Val = analogRead(PIN_A7);

//Button press events
if(pushLeftReset.update()){
  if(pushLeftReset.fallingEdge()){
    Keyboard.set_key1(KEY_R);
    Keyboard.send_now();
  } else if (pushLeftReset.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushRightReset.update()){
  if(pushRightReset.fallingEdge()){
    Keyboard.set_key1(KEY_COMMA);
    Keyboard.send_now();
  } else if (pushRightReset.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushSpeedUp.update()){
  if(pushSpeedUp.fallingEdge()){
    Keyboard.set_key1(KEY_T);
    Keyboard.send_now();
  } else if (pushSpeedUp.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushSpeedDown.update()){
  if(pushSpeedDown.fallingEdge()){
    Keyboard.set_key1(KEY_Y);
    Keyboard.send_now();
  } else if (pushSpeedDown.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushCardAdvance.update()){
  if(pushCardAdvance.fallingEdge()){
    Keyboard.set_key1(KEY_RIGHT_BRACE);
    Keyboard.send_now();
  } else if (pushCardAdvance.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushCardBack.update()){
  if(pushCardBack.fallingEdge()){
    Keyboard.set_key1(KEY_LEFT_BRACE);
    Keyboard.send_now();
  } else if (pushCardBack.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushMenu.update()){
  if(pushMenu.fallingEdge()){
    Keyboard.set_key1(KEYPAD_1);
    Keyboard.send_now();
  } else if (pushMenu.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushMenuToggle.update()){
  if(pushMenuToggle.fallingEdge()){
    Keyboard.set_key1(KEYPAD_2);
    Keyboard.send_now();
  } else if (pushMenuToggle.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushMenuSelect.update()){
  if(pushMenuSelect.fallingEdge()){
    Keyboard.set_key1(KEYPAD_3);
    Keyboard.send_now();
  } else if (pushMenuSelect.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushAccessoryHit.update()){
  if(pushAccessoryHit.fallingEdge()){
    Keyboard.set_key1(KEY_SPACE);
    Keyboard.send_now();
  } else if (pushAccessoryHit.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}
if(pushAccessoryReset.update()){
  if(pushAccessoryReset.fallingEdge()){
    Keyboard.set_key1(KEY_B);
    Keyboard.send_now();
  } else if (pushAccessoryReset.risingEdge()){
    Keyboard.set_key1(0);
    Keyboard.send_now();
  }
}

}
