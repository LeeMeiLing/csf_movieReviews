import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { MovieReviewsListComponent } from './components/movie-reviews-list.component';
import { PostCommentComponent } from './components/post-comment.component';

const routes: Routes = [
  {path:'', component:SearchComponent},
  {path:'searchresult/:movieName', component:MovieReviewsListComponent},
  {path:'comment/:movieName/:title', component:PostCommentComponent},
  {path:'**', redirectTo:'/', pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
