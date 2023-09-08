import { Component } from '@angular/core';
import { Client } from '@stomp/stompjs';

import { FormControl, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  messages: Array<string> = [];
  title = 'websocket-example';



  topicForm = new FormGroup({
    topic: new FormControl("topic")
  });

  constructor() {
    this.messages = [];
  }

  connectToWebSocket() {
    console.log("suscribing to websocket")
    let topicToConnectTo = this.topicForm.value.topic;
    console.log(topicToConnectTo)
    let client = new Client({
      brokerURL: 'ws://localhost:8080/cool-programmer-websocket',
      onConnect: () => {
        client.subscribe('/topic/greetings/' + topicToConnectTo, message => {
          this.messages.push(message.body);
          console.log(`Received: ${message.body}`)
        }
        );
        client.publish({ destination: '/greetings/12345', body: 'HelloAngular;12345' });
      },
    });
    client.activate();
  }



}

