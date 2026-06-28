import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CatalogueView {
  isbn: string;
  titre: string;
  auteur: string;
  nombreExemplaires: number;
}

@Injectable({
  providedIn: 'root'
})
export class QueryService {
  private http = inject(HttpClient);

  getCatalogue(): Observable<CatalogueView[]> {
    return this.http.get<CatalogueView[]>('/api/catalogue');
  }
}
