import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-post-comment',
  templateUrl: './post-comment.component.html',
  styleUrls: ['./post-comment.component.css']
})
export class PostCommentComponent implements OnInit, OnDestroy{

  form!: FormGroup
  param$!: Subscription
  title!: string
  movieName!:  string
  // apiUrl = environment.apiUrl

  constructor(private fb:FormBuilder, private httpclient:HttpClient, 
    private activateRoute:ActivatedRoute, private router: Router)
  {

  }
  ngOnDestroy(): void {
    if(this.param$){
      this.param$.unsubscribe
    }
  }


  ngOnInit(): void {

    this.param$ = this.activateRoute.params.subscribe({
      next: (params) => {
          this.title = params['title']
          this.movieName = params['movieName']
        }
    })

    this.form = this.fb.group({
      name:this.fb.control('',[Validators.required, Validators.pattern('[\\w]{3,}')]),
      rating:this.fb.control('', [Validators.required, Validators.min(1),Validators.max(5)]),
      comment:this.fb.control('',  [Validators.required])
    })
  }

  post(){

    const form = new HttpParams().set('title',this.title)
                                .set('name',this.form.value['name'])
                                .set('rating',this.form.value['rating'])
                                .set('comment',this.form.value['comment'])

    const headers = new HttpHeaders().set('Content-Type','application/x-www-form-urlencoded')
                                    .set('Accept','application/json')
    
                                 
    // this.httpclient.post(`${this.apiUrl}/api/comment`, form.toString() ,{ headers}).subscribe({
    this.httpclient.post(`/api/comment`, form.toString() ,{ headers}).subscribe({
      next: () => this.router.navigate(['/searchresult',this.movieName])
    })
  }

}
