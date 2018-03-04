package org.tthomas.dropdownautoselect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LOCATIONS);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.tv_auto_fill);
        textView.setAdapter(adapter);
    }

    private static final String[] LOCATIONS = new String[] {
            "John Crerar Library",
            "Kersten Physics Teaching Center",
            "Hinds Laboratory",
            "Arthur Rubloff Intensive Care Tower",
            "Marjorie B. Kovler Viral Oncology Laboratories",
            "Bookstore",
            "Surgery Brain Research Institute",
            "Abbott Memorial Hall",
            "Peck Pavilion",
            "Sylvain & Arma Wyler Children's Hospital",
            "Armour Clinical Research Building",
            "Franklin McLean Research Institute",
            "Goldblatt Memorial Hospital",
            "Hicks McElwee Hospital",
            "A.J. Carlson Animal Research Facility",
            "Albert Merrit Billings Hospital",
            "Bobs Roberts Memorial Hospital",
            "Charles Gilman Smith Hospital",
            "Goldblatt Pavilion",
            "Chicago Lying-In Hospital",
            "MRS Building (CLSC)",
            "Cummings Life Sciences Center",
            "Medical Campus Parking A",
            "Bernard A. Mitchell Hospital",
            "Magnetic Resonance Imaging Building",
            "Parking/Materials Management",
            "Duchossois Center for Advanced Medicine",
            "American School Building",
            "Gordon Center for Integrative Science",
            "Comer Children's Hospital",
            "Comer Center for Children and Specialty Care",
            "Center for Care and Discovery",
            "A Grid Grounds",
            "Science Quadrangle",
            "University Bookstore Plaza",
            "Vault at South Entrance to Goldblatt Pavillion",
            "Vault at Surgery Brain (NE)",
            "Vault at Surgery Brain (North)",
            "North High Vault (East)",
            "North High Vault (Center)",
            "North High Vault (West)",
            "Vault NW of Hinds",
            "Vault East of Crerar",
            "Vault North of GCIS",
            "Vault East of Comer II",
            "Vault on Ellis near Kersten",
            "Physics Research Center",
            "High Energy Physics",
            "Accelerator Building",
            "Biopsychological Research Building",
            "West Campus Combined Utility Plant",
            "UChicago Child Development Center - Drexel",
            "Temporary Academic Administration Center",
            "North Shop Trailer",
            "Medical Campus Parking B",
            "5600-12 South Maryland Avenue",
            "57th & Drexel Lot",
            "5601-03 South Drexel Lot",
            "5631-33 Maryland",
            "University Service Garage",
            "Stagg Field Building",
            "Donnelley Biological Sciences Learning Center",
            "Stagg Field",
            "BSLC Green House",
            "Jules F. Knapp Medical Research Center",
            "William Eckhardt Research Center",
            "Stagg Field Storage",
            "LASR Parking Lot",
            "Ratner Athletics Center",
            "Knapp Center for Biomedical Discovery",
            "B Grid Grounds",
            "Stagg Field Gate",
            "North Science Quadrangle",
            "Vault SW of Ratner",
            "Vault SW of Biopsychology",
            "Vault East of KCBD",
            "ERC Vault East of Building on Ellis",
            "ERC Vault West of Building",
            "ERC Vault North of BSLC",
            "ERC Vault North of BSLC-End of West Tunnel",
            "Henry Crown Field House",
            "Regenstein Library",
            "Bartlett Commons",
            "Young Memorial Building",
            "Smart Museum of Art",
            "Cochrane-Woods Art Center",
            "Court Theatre",
            "Young Parking Lot",
            "Joe and Rika Mansueto Library",
            "Campus North Residential Commons",
            "Campus North Parking Structure",
            "Max Palevsky Commons A/West",
            "Max Palevsky Commons B/Center",
            "Max Palevsky Commons C/East",
            "Psi Upsilon",
            "Disciples Divinity House",
            "University Church",
            "C Grid Grounds",
            "Regenstein Entry Garden",
            "Jean Block Garden",
            "Bartlett Quad",
            "Max Palevsky Courtyards",
            "Elden Sculpture Garden",
            "Avant Garden",
            "Vault at Bartlett (South)",
            "Vault at Bartlett (North)",
            "Vault West of Psi Upsilon",
            "Vault North of DDH",
            "Vault at Max Palevsky (SE)",
            "Vault at Max Palevsky (East)",
            "Vault at Max Palevsky (NE)",
            "Vault SW of Young",
            "Quadrangle Club",
            "Mitchell Tower",
            "Reynolds Clubhouse",
            "Hutchinson Commons",
            "Zoology Building",
            "Anatomy Building",
            "Hitchcock Hall",
            "Snell Hall",
            "Searle Chemistry Laboratory",
            "Culver Hall",
            "Erman Biology Center",
            "Mandel Hall",
            "5727 South University Avenue",
            "5733 South University Avenue",
            "Eckhart Hall",
            "Ryerson Laboratory",
            "Kent Chemical Laboratory",
            "Jones Laboratory",
            "Edward H. Levi Hall",
            "Cobb Lecture Hall",
            "Bond Chapel",
            "Swift Hall",
            "Rosenwald Hall",
            "Walker Museum",
            "Oriental Institute",
            "Rockefeller Chapel",
            "5855 South University Avenue",
            "Beecher Hall",
            "Green Hall",
            "Kelly Hall",
            "Foster Hall",
            "Social Science Research Building",
            "Stuart Hall",
            "Harper Memorial Library",
            "Haskell Hall",
            "Wieboldt Hall",
            "Classics Building",
            "Goodspeed Hall",
            "Gates Hall",
            "Blake Hall",
            "Stevanovich Institute on the Formation of Knowledge",
            "5855 University Coach House",
            "Pick Hall",
            "Lexington Parking Lot",
            "Calvert House",
            "Alpha Delta Phi",
            "Cobb Gate",
            "Ryerson Parking Lot",
            "5733 South University Parking Lot",
            "D Grid Grounds",
            "Botany Pond",
            "C Bench",
            "Cobb Gate Gardens",
            "Harper Quad",
            "Hitchcock Court",
            "Hull Court West",
            "Hutchinson Court",
            "Social Science Quad",
            "Classics Quad",
            "Main Quad (Landscape)",
            "Rockefeller Chapel Gardens and Entry Walk",
            "Woodlawn Garden",
            "Oriental Insitute Courtyard",
            "Stephanie Shambaugh Kramer Beds",
            "Dr. Janet Rowley Garden",
            "Dan Hall Garden",
            "Circle Garden",
            "Quadrangle Entry Gardens",
            "Swift Hall Courtyard",
            "Main Quadrangle Block",
            "Quad Club Landscape",
            "Vault East of Rockefeller Chapel",
            "Vault West of President's House",
            "Vault East of Eckhart",
            "Vault East of Mandel Hall",
            "Vault NW of Math-Stat",
            "Vault SW of Quad Club",
            "Vault East of Reynolds",
            "Vault at Bond Chapel",
            "Electrical Vault West of Rockefeller Chapel",
            "Lillie House",
            "Sunny Gymnasium",
            "Belfield Hall",
            "Ida Noyes Hall",
            "Judd Hall",
            "University High School",
            "Blaine Hall",
            "International House",
            "Wilder House",
            "Breckinridge House",
            "Stony Island Hall",
            "5700-5708 Stony Island Parking Lot",
            "Middle School",
            "Gordon Parks Arts Hall",
            "Judd Parking Lot",
            "Cloisters",
            "Stein Place",
            "Kovler Gymnasium",
            "5721 Kenwood Parking Lot",
            "Cloisters Parking Lot",
            "Charles M. Harper Center",
            "International House Parking Lot"
    };

}