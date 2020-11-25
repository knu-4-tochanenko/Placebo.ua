import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DrugsComponent } from './component/drugs/drugs.component';
import { AppComponent } from './app.component';
import { AboutComponent } from './component/about/about.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { MainComponent } from './component/main/main.component';



const routes: Routes = [
  { path: '', redirectTo: '/main', pathMatch: 'full' },
  //{ path: '**', component: PlayerComponent },
  {path: 'main', component: MainComponent },
  {path: 'drugs', component: DrugsComponent},
  {path: 'about', component: AboutComponent},
  {path: 'footer', component: FooterComponent},
  {path: 'header', component: HeaderComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
