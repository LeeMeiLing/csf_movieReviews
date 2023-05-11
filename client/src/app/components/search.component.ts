import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Review } from '../model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form!: FormGroup

  constructor(private fb: FormBuilder, private router:Router){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm(): FormGroup {
    return this.fb.group({
      // movieName: this.fb.control('',[Validators.required, Validators.pattern('[a-zA-Z0-9][a-zA-Z0-9]*')])
      movieName: this.fb.control('',[Validators.required, Validators.pattern('[\\w]{2,}')])

    })
  }

  search(){
    console.log('>>> in search()', this.form.value['movieName'])

    this.router.navigate(['/searchresult',this.form.value['movieName']])
  }

}
