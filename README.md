# PersianName


PersianName is a transliteration tool to convert Persian name to Latin names.
It is based on algorithms such as:

  - Global Edit Distance
  - Local Edit Distance
  - N-Gram
  - And optimization methods with substitution matrixes
  - Self-Learning Substitution Matrix

# How to run the code

  - Change file output directory in tools/Config -------FILE_DIR_PATH
  - Run main.java in the project
  - Check the output directory


You can also:
  - Modify matrixes in algorithm/EditDistance.java needlemanWunsch() and smithWaterman() equal function
  - Use training mode in the main() function
  - Run algorithm/FeatureSelection.java to see feature selection results
  - Adjust output size for each Persian name prediction by editing tools/Config -------MAX_RESULT_ARRAY
  - Adjust number of thread which run the program by changing tools/Config -------MAX_THREAD

# Format of Output
The console output prints out the accuracy/recall, which is not provided in the output file.
Each line in the output file is like this: 
"persian_name_id persian_name[equivalent_latin_name] predicted_latin_name score"
The "true"/"false" indicates whether there is one of the predictions for a Persian name above which is correct.
The example of an output file is as below.

1 aaltj[aeltje] aaltje 5
1 aaltj[aeltje] aaltruide 4
1 aaltj[aeltje] gaal 3
1 aaltj[aeltje] aali 3
1 aaltj[aeltje] muralt 3
-------------------false-------------------------
2 aamyna[aamina] aamina 4
2 aamyna[aamina] myrnas 3
2 aamyna[aamina] myrna 3
2 aamyna[aamina] bellamys 3
2 aamyna[aamina] bellamy 3
-------------------true-------------------------
3 aarvn[aaron] aaron 3
3 aarvn[aaron] carver 3
3 aarvn[aaron] carvallo 3
3 aarvn[aaron] carvajal 3
3 aarvn[aaron] ainsaar 3
-------------------true-------------------------
