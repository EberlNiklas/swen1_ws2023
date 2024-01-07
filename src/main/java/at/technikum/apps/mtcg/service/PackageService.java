package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.repository.PackageRepository;

public class PackageService {

private final PackageRepository packageRepository;

public PackageService(){
    this.packageRepository = new DatabasePackageRepository();
}



}
