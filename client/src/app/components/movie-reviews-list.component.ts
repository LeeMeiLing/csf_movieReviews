import { Component, OnDestroy, OnInit } from '@angular/core';
import { Review } from '../model';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-movie-reviews-list',
  templateUrl: './movie-reviews-list.component.html',
  styleUrls: ['./movie-reviews-list.component.css']
})
export class MovieReviewsListComponent implements OnInit, OnDestroy{

  reviewList!: Review[]
  param$!: Subscription
  movieName!: string
  noResultFound = false
  // apiUrl = environment.apiUrl

  constructor(private activatedRoute:ActivatedRoute, private httpclient:HttpClient, private router:Router){}

  ngOnDestroy(): void {
    if(this.param$){
      this.param$.unsubscribe
    }
  }
  
  ngOnInit(): void {

    this.param$ = this.activatedRoute.params.subscribe({
      next: async (params) => {
              this.movieName = params['movieName']
              await this.getReviews()
            },
      error: (err) => {
        console.log("in ngOnInit catch: noResult is now: ", this.noResultFound)
        console.error(err)
        this.noResultFound = true
      }
        
    })
   
  }
  
  getReviews(){
 
    const params = new HttpParams().set('query', this.movieName)
    const headers = new HttpHeaders().set('Accept','application/json')
    // this.httpclient.get<Review[]>(`${this.apiUrl}/api/search`,{ headers, params }).subscribe({
    this.httpclient.get<Review[]>(`/api/search`,{ headers, params }).subscribe({

      next: v => {
        this.reviewList = v as Review[]
        console.log('>>in getReviews()', this.reviewList)
      },
      error: (err) => {
        this.noResultFound = true
        console.log('>>in getReviews() error')
        console.error(err)
      },
      complete: () => console.log('returned from server')
    })
    
  }

  goCommentPage(title:string){
    this.router.navigate(['/comment',this.movieName,title])
  }


}
