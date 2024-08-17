import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { ElementRef, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  user = new User();

  constructor(private renderer: Renderer2, private elementRef: ElementRef) {}
    
  // Function to make an element invisible
  makeInvisible(elementClass: string) {
    const element = this.elementRef.nativeElement.querySelector('.' + elementClass);
    if (element) {
      this.renderer.addClass(element, 'invisible');
    }
  }

  ngOnInit() {
    if(sessionStorage.getItem('userdetails')){
      this.user = JSON.parse(sessionStorage.getItem('userdetails') || "");
    }
  }
  

}
