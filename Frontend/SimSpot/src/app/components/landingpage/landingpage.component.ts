import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css']
})
export class LandingpageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }


  //hiding info box
  visible:boolean = false


  //onclick toggling both
  onclick()
  {
     //not equal to condition
    this.visible = !this.visible
  }
}
